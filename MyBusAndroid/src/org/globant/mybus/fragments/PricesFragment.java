package org.globant.mybus.fragments;

import org.globant.data.DBTickets;
import org.globant.mybus.R;
import org.globant.mybus.adapters.TicketsAdapter;

import android.view.View;
import android.widget.ListView;

import com.globant.roboneckUI.base.BaseFragment;

public class PricesFragment extends BaseFragment
{
	DBTickets tDBA;

	ListView lstTickets;

	private TicketsAdapter adapter;

	@Override
	public void onRefresh()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int getFragmentLayoutForCreateView()
	{
		return R.layout.fragment_prices;
	}

	@Override
	public void onCreatedNeckView(View inflatedView)
	{
		disableRefreshSwipe();

		tDBA = new DBTickets(getBaseActivity());

		tDBA.WritableMode();

		if (tDBA.getList().isEmpty())
		{
			tDBA.Insert(3.97, "Local (Todas las líneas)");
			tDBA.Insert(5.19, "542 - Aquasol");
			tDBA.Insert(4.25, "542 - Estación Camet");
			tDBA.Insert(5.04, "715/720 - Batán");
			tDBA.Insert(5.51, "715/720 - Chapadmalal");
			tDBA.Insert(5.67, "717 - Ruta 226, km. 10");
			tDBA.Insert(5.19, "717 - Ruta 226, km. 13");
			tDBA.Insert(5.67, "717 - Country Club");
			tDBA.Insert(6.29, "717 - San Carlos");
			tDBA.Insert(6.29, "717 - Colinas Verdes");
			tDBA.Insert(6.29, "717 - S. de los Padres");
			tDBA.Insert(4.25, "511 - Golf Club");
			tDBA.Insert(5.19, "511 - Col. Chapadmalal");
			tDBA.Insert(5.67, "511 - San Eduardo");
		}

		lstTickets = (ListView) inflatedView.findViewById(R.id.list);

		adapter = new TicketsAdapter(getBaseActivity(),
				R.layout.list_tickets_row);

		lstTickets.setAdapter(adapter);

	}

}
