package com.tj.playstorecopycat;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.tj.playstorecopycat.databinding.ActivityAppDetailBinding;
import com.tj.playstorecopycat.datas.App;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class AppDetailActivity extends AppCompatActivity {

    App mAppData; // 이 화면에서 다룰 앱의 정보를 가진 멤버변수

    ActivityAppDetailBinding act;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = DataBindingUtil.setContentView(this, R.layout.activity_app_detail);


//        String appTitle = getIntent().getStringExtra("제목");
//        String appCompanyName = getIntent().getStringExtra("회사이름");

        mAppData = (App) getIntent().getSerializableExtra("앱정보");


        act.appTitleTxt.setText(mAppData.title);
        act.companyNameTxt.setText(mAppData.companyName);

        act.userRatingTxt.setText(String.format("%d점", mAppData.userRating));

//        구매 여부에 따라 필요한 버튼만 보여주기

        if (mAppData.isMine) {
            act.removeBtn.setVisibility(View.VISIBLE);
            act.launchBtn.setVisibility(View.VISIBLE);
            act.purchaseBtn.setVisibility(View.GONE);
        } else {
            act.removeBtn.setVisibility(View.GONE);
            act.launchBtn.setVisibility(View.GONE);
            act.purchaseBtn.setVisibility(View.VISIBLE);

//            구매하기 버튼의 문구도 올바른 가격으로
            act.purchaseBtn.setText(String.format("구매하기(%,d원)", mAppData.price));

        }

        act.dialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri phoneUri = Uri.parse("tel:010-9876-5432");
                Intent intent = new Intent(Intent.ACTION_DIAL, phoneUri);
                startActivity(intent);

            }
        });

act.smsBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Uri smsUri =Uri.parse("smsto:01012345678");
        Intent intent = new Intent(Intent.ACTION_SENDTO,smsUri);
        intent.putExtra("sms_body", "미리 작성될 메세지");
        startActivity(intent);
    }
});
act.goHomepageBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Uri uri = Uri.parse("https://www.naver.com/");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);

    }
});
act.purchaseBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Uri uri = Uri.parse("market://details?id=com.supercell.brawlstars");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }
});
        act.dateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("텍스트뷰 클릭", "실제로 동작하나?");
                DatePickerDialog dpd = new DatePickerDialog(AppDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar cal =  Calendar.getInstance();
                        cal.set(Calendar.YEAR,year);
                        cal.set(Calendar.MONTH,month);
                        cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        cal.set(year,month,dayOfMonth);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 M월 d일");

                        String datestr = sdf.format(cal.getTimeInMillis());
                        act.dateTxt.setText(datestr);

                    }
                }, 2019, 3, 27);

                dpd.show();

            }
        });
        act.timeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(AppDetailActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.HOUR_OF_DAY,hourOfDay);
                            cal.set(Calendar.MINUTE,minute);
                            SimpleDateFormat sdf = new SimpleDateFormat("a h시 m분");

                            String timestr = sdf.format(cal.getTimeInMillis());
                            act.timeTxt.setText(timestr);
                    }
                }, 3,15,true);
                tpd.show();
            }
        });

//        날짜를 누르고 선택하면 반영되게
//        시간을 누르고 선택하면 반영 되게, AM/PM 을 오전/ 오후로 변경.
    }
}
