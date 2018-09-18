package shahin.android.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import shahin.android.exam.data.Constants;
import shahin.android.exam.data.DeliverablesListAdapter;
import shahin.android.exam.R;

public class DeliverablesListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverables_list);

        initDeliverablesList();
    }

    private void initDeliverablesList() {
        ListView lvDeliverables = (ListView) findViewById(R.id.list_deliverables);
        DeliverablesListAdapter adapter = new DeliverablesListAdapter(this);
        lvDeliverables.setAdapter(adapter);
        lvDeliverables.setOnItemClickListener(this);
        adapter.addAll(MainApplication.mDeliverList);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent mapIntent = new Intent(this, DeliverLocationMapActivity.class);
        mapIntent.putExtra(Constants.KEY_DELIVERABLES_POSITION, position);
        startActivity(mapIntent);
    }
}
