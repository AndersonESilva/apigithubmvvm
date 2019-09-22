package com.anderson.apigithub_mvvm.feature.pullRequest.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import com.anderson.apigithub_mvvm.R

import kotlinx.android.synthetic.main.activity_pull_request.*

class PullRequestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pull_request)
        setSupportActionBar(toolbar)


    }

}
