package com.example.gmb.myapplication;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import opengles.AdrenoGLView;

public class AdrenoActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_adreno);

        // Cria uma instância do OpenGL surface view e utiliza como layout para essa activity
        glSurfaceView = new AdrenoGLView(this);
        setContentView(glSurfaceView);
    }
}
