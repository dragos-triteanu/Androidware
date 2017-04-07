package com.enginizer.activities;

import android.Manifest;
import android.accounts.Account;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract.Calendars;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;


import com.enginizer.EnginizerApplication;
import com.enginizer.R;
import com.enginizer.service.ScheduleService;
import com.enginizer.util.AddressBookHelper;
import com.enginizer.util.CallConstants;
import com.enginizer.util.PreferencesHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Stack;

public class ReScheduleActivity extends EnginizerActivity implements AddressBookHelper.AddressBookListener {

	public static final String TAG = "ReScheduleActivity:";

	public static final String MEMO_PREFS_NAME = "memoPreferences";
	public static final String REMINDER_URI_SCHEME = "callerq";

	private static final int CALENDAR_EVENT_CALL_LENGTH_MINUTES = 15;
	private static final int CALENDAR_EVENT_MEETING_LENGTH_MINUTES = 60;

	// form fields
	private static EditText nameField;
	private static EditText companyField;
	private static EditText memoField;
	private static Button dateField;
	private static Button timeField;
	private static CheckBox meetingField;
	private static TextView nameText;
	private static TextView companyText;
	private static ViewGroup nameArea;
	private static QuickContactBadge quickContactBadge;
	private static ScrollView scrollView;

	// calendar that will store the scheduled date for the call
	private static Calendar scheduledDate = Calendar.getInstance();

	// start and end time for the call (retrieved from the broadcast receiver)
	private static Calendar callStartTime;
	private static Calendar callStopTime;

	// the client's phone number
	private static String phoneNumber;

	// the client's contact object
	private AddressBookHelper.Contact contact;

	// add contact to ignore list flag
	private static boolean neverAgainChecked = false;

	// stack that will hold any additional calls intents that might pop-up
	private static Stack<Intent> intentStack = new Stack<Intent>();

	// request identifier received when asking contact details
	private String getContactRequestId;

	private GestureDetector scrollDetector;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reschedule);


		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		manager.cancel(ScheduleService.NOTIFICATION_ID);

		nameField = (EditText) findViewById(R.id.nameField);
		memoField = (EditText) findViewById(R.id.memoField);
		timeField = (Button) findViewById(R.id.timeField);
		dateField = (Button) findViewById(R.id.dateField);
		meetingField = (CheckBox) findViewById(R.id.meetingField);
		companyField = (EditText) findViewById(R.id.companyField);
		nameText = (TextView) findViewById(R.id.nameText);
		companyText = (TextView) findViewById(R.id.companyText);
		nameArea = (ViewGroup) findViewById(R.id.nameArea);
		quickContactBadge = (QuickContactBadge) findViewById(R.id.quickContactBadge);
		scrollView = (ScrollView) findViewById(R.id.scrollview);

		if (scrollView != null) {
			scrollDetector = new GestureDetector(this, new ScrollGestureListener());
			scrollView.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					return scrollDetector.onTouchEvent(event);
				}
			});
		}

		AddressBookHelper.getInstance().addListener(this);

		handleIntent();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		AddressBookHelper.getInstance().removeListener(this);
	}

	@Override
	public void contactRetrieved(String requestId, AddressBookHelper.Contact contact) {
		if (getContactRequestId.equals(requestId)) {
			this.contact = contact;
			if (contact != null) {
				nameText.setText(contact.name);
				companyText.setText(contact.company);

				// set the old memo
				SharedPreferences memos = getSharedPreferences(MEMO_PREFS_NAME, 0);
				String oldMemo = memos.getString(phoneNumber + contact.name, null);
				if (oldMemo != null) {
					memoField.setText(oldMemo);
				}
				memoField.selectAll();

			} else {
				nameArea.setVisibility(View.GONE);
				nameField.setVisibility(View.VISIBLE);
				companyField.setVisibility(View.VISIBLE);
			}
		}

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		// push the old intent on the stack
		intentStack.push(getIntent());
		setIntent(intent);
		handleIntent();
	}

	private void handleIntent() {

		// get the phone number out of the intent
		phoneNumber = getIntent().getStringExtra(CallConstants.INTENT_PHONE_NUMBER);

		// get the call start time and call end time
		callStartTime = (Calendar) getIntent().getSerializableExtra(
				CallConstants.INTENT_CALL_STARTED_TIME);
		callStopTime = (Calendar) getIntent().getSerializableExtra(
				CallConstants.INTENT_CALL_STOP_TIME);

		// set the contact badge photo
		quickContactBadge.assignContactFromPhone(phoneNumber, true);

		// retrieve contact data based on phone number
		AddressBookHelper addressBookHelper = AddressBookHelper.getInstance();
		getContactRequestId = addressBookHelper.getContact(this, phoneNumber);

		// set the date and time field default values
		scheduledDate = Calendar.getInstance();

		scheduledDate.add(Calendar.HOUR_OF_DAY, 1);

		setTimeFieldValue(scheduledDate.getTime());
		setDateFieldValue(scheduledDate.getTime());
	}

	/** button actions */
	public void showTimePickerDialog(View v) {
		DialogFragment newFragment;

		// on later Android versions, show the radial time picker
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
//			// Use the current time as the default values for the picker
//			int hour = scheduledDate.get(Calendar.HOUR_OF_DAY);
//			int minute = scheduledDate.get(Calendar.MINUTE);
//
//			com.android.datetimepicker.time.TimePickerDialog.OnTimeSetListener listener = new com.android.datetimepicker.time.TimePickerDialog.OnTimeSetListener() {
//				@Override
//				public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//					commonOnTimeSet(hourOfDay, minute);
//				}
//			};
//			com.android.datetimepicker.time.TimePickerDialog picker = new com.android.datetimepicker.time.TimePickerDialog();
//			picker.initialize(listener, hour, minute, DateFormat.is24HourFormat(this));
//			newFragment = picker;
//		} else {
//			newFragment = new TimePickerFragment();
//		}
//		newFragment.show(getSupportFragmentManager(), "timePicker");
	}

	public void showDatePickerDialog(View v) {
//		DialogFragment newFragment;
//		// on later Android versions, show the radial time picker
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
//			// Use the current time as the default values for the picker
//			// Use the current date as the default date in the picker
//			int year = scheduledDate.get(Calendar.YEAR);
//			int month = scheduledDate.get(Calendar.MONTH);
//			int day = scheduledDate.get(Calendar.DAY_OF_MONTH);
//
//			com.android.datetimepicker.date.DatePickerDialog.OnDateSetListener listener = new com.android.datetimepicker.date.DatePickerDialog.OnDateSetListener() {
//				@Override
//				public void onDateSet(com.android.datetimepicker.date.DatePickerDialog dialog,
//						int year, int monthOfYear, int dayOfMonth) {
//					commonOnDateSet(year, monthOfYear, dayOfMonth);
//				}
//			};
//			com.android.datetimepicker.date.DatePickerDialog picker = new com.android.datetimepicker.date.DatePickerDialog();
//			picker.initialize(listener, year, month, day);
//			newFragment = picker;
//		} else {
//			newFragment = new DatePickerFragment();
//		}
//		newFragment.show(getSupportFragmentManager(), "datePicker");
	}

	public void onNeverAgainToggle(View v) {
		neverAgainChecked = ((CheckBox) v).isChecked();
		Button saveButton = (Button) findViewById(R.id.saveButton);
		if (neverAgainChecked) {
			saveButton.setEnabled(false);
		} else {
			saveButton.setEnabled(true);
		}
	}

	public void onCancelClick(View v) {
		if (neverAgainChecked) {
			PreferencesHelper.ignorePhoneNumber(this, phoneNumber);
		}
		finishProcessingIntent();
	}

	public void onSaveClick(View v) {

		if (contact == null) {
			// perform validation
			if (nameField.getText().toString().trim().length() == 0) {
				//nameField.setError(getString(R.string.error_name_required));
				return;
			}

			contact = new AddressBookHelper.Contact();
			contact.name = nameField.getText().toString();
			contact.company = companyField.getText().toString();
			ArrayList<String> phonesList = new ArrayList<String>();
			phonesList.add(phoneNumber);
			contact.phoneNumbers = phonesList;
			contact.email = "";

			AddressBookHelper.getInstance().addContact(this, contact);
		}
//
//		Reminder reminder = new Reminder();
//		reminder.setMemoText(memoField.getText().toString());
//		reminder.setMeeting(meetingField.isChecked());
//		reminder.setCreatedDatetime(System.currentTimeMillis());
//		reminder.setScheduleDatetime(scheduledDate.getTimeInMillis());
//		reminder.setCallStartDatetime(callStartTime.getTimeInMillis());
//		reminder.setCallDuration((int) ((callStopTime.getTimeInMillis() - callStartTime
//				.getTimeInMillis()) / 1000));
//		reminder.setContactName(contact.name);
//		reminder.setContactCompany(contact.company);
//		reminder.setContactPhones(contact.phoneNumbers);
//		reminder.setContactEmail(contact.email);
//
//		setAlarm(reminder);
//
//		// if the OS version is at least Ice Cream Sandwich
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//			try {
//				setCalendarEvent(reminder);
//			} catch (Exception e) {
//				if (BuildConfig.DEBUG) {
//					Log.e(TAG, e.toString());
//				}
//			}
//		}
//
//		// save the reminder to the local database and attempt upload
//		DatabaseHelper.getInstance().saveReminder(this, reminder);
//
//		// save the last memo in order to display it next time
//		SharedPreferences memos = getSharedPreferences(MEMO_PREFS_NAME, 0);
//		SharedPreferences.Editor editor = memos.edit();
//		editor.putString(phoneNumber + contact.name, reminder.getMemoText());
//		editor.commit();

		finishProcessingIntent();
	}

	private static void setTimeFieldValue(Date dateTimeValue) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
		timeField.setText(sdf.format(dateTimeValue));
	}

	private static void setDateFieldValue(Date dateValue) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
		dateField.setText(sdf.format(dateValue));
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	private Long getFirstCalendarId(Account account) {

		// try to find the main calendar
		Cursor cur = null;
		ContentResolver cr = getContentResolver();
		Uri uri = Calendars.CONTENT_URI;
		String selection = "((" + Calendars.ACCOUNT_NAME + " = ?) AND (" + Calendars.ACCOUNT_TYPE
				+ " = ?) AND (" + Calendars.OWNER_ACCOUNT + " = ?))";
		String[] selectionArgs = new String[]{account.name, account.type, account.name};

		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return 0L;
		}
		cur = cr.query(uri, new String[]{Calendars._ID}, selection, selectionArgs, null);

		if (cur.moveToFirst() == false) {
			return null;
		}

		long calendarId = cur.getLong(cur.getColumnIndex(Calendars._ID));
		cur.close();

		return calendarId;
	}

	private void finishProcessingIntent() {
		if (intentStack.isEmpty()) {
			finish();
		} else {
			setIntent(intentStack.pop());
			handleIntent();
		}
	}

	@Override
	void injectDependencies() {
		EnginizerApplication.APP.inject(this);
	}

	public static class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			int hour = scheduledDate.get(Calendar.HOUR_OF_DAY);
			int minute = scheduledDate.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			commonOnTimeSet(hourOfDay, minute);
		}

	}

	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			int year = scheduledDate.get(Calendar.YEAR);
			int month = scheduledDate.get(Calendar.MONTH);
			int day = scheduledDate.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			commonOnDateSet(year, month, day);
		}
	}

	private class ScrollGestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

			if (Math.abs(distanceY) > 10) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
			}

			return false;
		}
	}

	private static void commonOnTimeSet(int hourOfDay, int minute) {
		// Do something with the time chosen by the user
		scheduledDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
		scheduledDate.set(Calendar.MINUTE, minute);
		// scheduledDate.set(Calendar.SECOND, 0);
		setTimeFieldValue(scheduledDate.getTime());
	}

	private static void commonOnDateSet(int year, int month, int day) {
		// Do something with the date chosen by the user
		scheduledDate.set(year, month, day);
		setDateFieldValue(scheduledDate.getTime());
	}

}
