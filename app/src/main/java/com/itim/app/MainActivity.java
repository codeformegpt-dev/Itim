package com.itim.app;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.itim.app.data.AppDatabase;
import com.itim.app.data.SourceDao;
import com.itim.app.model.content.Source;

import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView dbText = findViewById(R.id.dbText);

        Executors.newSingleThreadExecutor().execute(() -> {
            SourceDao dao = AppDatabase.getInstance(getApplicationContext()).sourceDao();
            String[] labels = {"תורה", "משנה", "נביאים", "כתובים", "תלמוד בבלי", "תלמוד ירושלמי"};
            for (int i = 0; i < labels.length; i++) {
                if (dao.getById(i + 1) == null) {
                    dao.insert(new Source(i + 1, labels[i]));
                }
            }

            List<Source> sources = dao.getAll();
            Log.d("ItimDb", "sources in db = " + sources.size());

            StringBuilder sb = new StringBuilder();
            sb.append("טבלת source (").append(sources.size()).append(" רשומות)\n\n");
            sb.append("id  |  label\n");
            sb.append("-----------------\n");
            for (Source s : sources) {
                sb.append(s.getId()).append("  |  ").append(s.getLabel()).append("\n");
            }

            runOnUiThread(() -> dbText.setText(sb.toString()));
        });
    }
}