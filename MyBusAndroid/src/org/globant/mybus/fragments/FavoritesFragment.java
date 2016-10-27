package org.globant.mybus.fragments;

import org.globant.data.DBLocations;
import org.globant.models.Location;
import org.globant.mybus.R;
import org.globant.mybus.adapters.FavoritesAdapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.globant.roboneckUI.base.BaseFragment;

public class FavoritesFragment extends BaseFragment implements AdapterView.OnItemClickListener
{
	FavoritesAdapter adapter;

	ListView lstLocations;

	DBLocations lDBA;

	Button btnPurge;
	
	@Override
	public void onRefresh()	{ }

	@Override
	public int getFragmentLayoutForCreateView()
	{
		return R.layout.fragment_favorites;
	}

	@Override
	public void onCreatedNeckView(View inflatedView)
	{
		disableRefreshSwipe();
		lDBA = new DBLocations(getBaseActivity());

		btnPurge = (Button) inflatedView.findViewById(R.id.btnPurge);
		
		btnPurge.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						getBaseActivity());

				// set title
				alertDialogBuilder.setTitle(R.string.confirm);

				// set dialog message
				alertDialogBuilder.setMessage(R.string.warning_trash_favorites)
						.setCancelable(false)
						.setPositiveButton("Sí", new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int id)
							{
								// if this button is clicked, close
								// current activity
								// activity.this.finish();
								lDBA.WritableMode();
								
								lDBA.DeleteFavorites();
								
								lDBA.Close();
								
								adapter.getItems().clear();
								
								adapter.notifyDataSetChanged();
								
							}
						})
						.setNegativeButton("No", new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int id)
							{
								// if this button is clicked, just close
								// the dialog box and do nothing
								dialog.cancel();
							}
						});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			}
		});
		
//		lDBA.WritableMode();	
//		lDBA.Insert(-38.092620, -57.548429, "Vélez Sarsfield y La Costa", 0, 0, 1, "Globant");
//		lDBA.Insert(-38.054758, -57.546904, "Av Martínez de Hoz 1300", 0, 0, 1, "Club Quilmes");
//		lDBA.Insert(-38.018338, -57.525425, "Falucho 99", 0, 0, 1, "Abuela");
//		lDBA.Insert(-38.005557, -57.542420, "Arenales 2136", 0, 0, 1, "Casa de la Tia");
//		lDBA.Insert(-38.007242, -57.561496, "Av Independencia 3050", 0, 0, 1, "Casa de Jose");
//		lDBA.Insert(-37.997327, -57.548705, "Av Pedro Luro 2841", 0, 0, 1, "Universidad");
		
		lstLocations = (ListView) inflatedView.findViewById(R.id.list);
		
		lstLocations.setOnItemClickListener(this);
	
		load();
		
		if (adapter.getCount() == 0)
		{
			Toast.makeText(getBaseActivity(), getString(R.string.favorites_list_empty), Toast.LENGTH_LONG).show();
		}
	}

	private void load()
	{
		adapter = new FavoritesAdapter(this.getActivity(),
				R.layout.list_location_row);

		lstLocations.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getBaseActivity());

		// set title
		alertDialogBuilder.setTitle(R.string.confirm);

		// set dialog message
		alertDialogBuilder.setMessage(R.string.deletion_confirmation)
				.setCancelable(false)
				.setPositiveButton("Sí", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						// if this button is clicked, close
						// current activity
						// activity.this.finish();
						Location fav = (Location) adapter.getItems().get(position);
						
						lDBA.WritableMode();
						
						lDBA.Delete(fav.getId());
						
						lDBA.Close();
						
						adapter.getItems().remove(position);
						
						adapter.notifyDataSetChanged();
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}
}
