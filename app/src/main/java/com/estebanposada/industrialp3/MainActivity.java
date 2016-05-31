package com.estebanposada.industrialp3;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    SeekBar seekBar;
    TextView textView;
    TextView textrama, probetv;
    Button run;
    Button stop;
    String Myfreq = "0000", Mymotor, sense="047c";
    String probe, probe2;
    //Integer probeint;
    Double probeint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        run = (Button) findViewById(R.id.run);
        stop = (Button) findViewById(R.id.stop);
        probetv = (TextView) findViewById(R.id.probe);

        setSeekBar();
        textrama = (TextView) findViewById(R.id.trama);
        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                probe2 = "0206"+Mymotor+sense+Myfreq;
                textrama.setText(probe2);

                //probeint = checksum(probe2);
                probe = checksum(probe2);
                probetv.setText(probe);

                //probetv.setText(String.valueOf(probeint));
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textrama.setText("0206 " + Mymotor + " " + sense + " " + "0000" + " CS");
            }
        });

        spinner = (Spinner)findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.motors, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, parent.getItemAtPosition(position).toString()+"selected", Toast.LENGTH_SHORT).show();
                Mymotor = "0"+String.valueOf(position+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void sense(View view){
        boolean check = ((RadioButton) view).isChecked();

        switch (view.getId())
        {
            case R.id.s1:
                if (check){
                    sense = "047c";
                }
                break;
            case R.id.s2:
                if (check){
                    sense = "847c";
                }
        }

    }

    public void setSeekBar(){
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        textView = (TextView) findViewById(R.id.txbar);
        seekBar.setMax(60);
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int prog_val;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        prog_val = progress;
                        textView.setText("Freq: " + progress + " / " + seekBar.getMax());
                        Myfreq = String.format("%04X", (0xFFFF) & (Math.round(progress * 136.56)));
                        //probe = String.format("%04X", (0xFFFF) & (Math.round(progress*136.56)));
                        //Toast.makeText(MainActivity.this, "Frequency: " + progress, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        //Toast.makeText(MainActivity.this, "Frequency Start", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textView.setText("Freq: " + prog_val + " / " + seekBar.getMax());
                    }
                }
        );
    }

    public String checksum (String p2){
        Double numb;
        Double n1, n2, n3, n4, n5;
        String cs = "1234";

        numb = Double.parseDouble(p2.replaceAll("[\\D]", "12"));
        cs.split(p2);
//        numb = Integer.parseInt(p2);
        //numb = Double.parseDouble(p2);
        n1=numb/10;

        //cs = String.valueOf(size);
        //cs = String.valueOf(p2.split(p2,2));
        //numb = Integer.parseInt(cs);
        return p2;
        //return n1;
    }
}
