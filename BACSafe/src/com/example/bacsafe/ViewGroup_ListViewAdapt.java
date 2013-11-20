package com.example.bacsafe;

import java.util.LinkedList;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Custom List View Adapter for View Group Page.  Used to display User's username, first and last name, BAC and total drink count
 * 
 * @author Team BAC Safe
 */
public class ViewGroup_ListViewAdapt extends BaseAdapter {

	private LinkedList<Buddy> m_listGroupBuddies;
	Context context;

	/**
	 * Constructor.
	 * @param listBuddies - List of Buddies in the group
	 * @param c - Context from called class
	 */
	ViewGroup_ListViewAdapt (LinkedList<Buddy> listBuddies, Context c){
		m_listGroupBuddies = listBuddies;
		context = c;
	} // ViewGroup_ListViewAdapt()

	/**
	 * Returns number of buddies in the group
	 * @return m_listGroupBuddies.size() - Number of buddies in the group
	 */
	public int getCount() {
		// TODO Auto-generated method stub
		return m_listGroupBuddies.size();
	} // getCount()


	/**
	 * Gets current Buddies index from list
	 * @return m_listGroupBuddies.get(position) - Index of Buddy in list
	 */
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return m_listGroupBuddies.get(position);
	} // getItem()

	/**
	 * Returns List ID for Buddy specific index/position in list
	 * @return position - index value
	 */
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	} // getItemId()

	/**
	 * Sets the UI for the Custom List View
	 * @param position
	 * @param convertView
	 * @param parent
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View viewGroupBuddies = convertView;
		if (viewGroupBuddies == null) 
		{
			LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			viewGroupBuddies = vi.inflate(R.layout.custom_listview_view_group, null);
		}

		Buddy buddy = m_listGroupBuddies.get(position);

		// Combine the First and Last name of the user if provided
		String sNameCombined = " ";
		String sFirstName = buddy.m_sBuddyFirstName;
		String sLastName = buddy.m_sBuddyLastName;
		if(sFirstName != null)
		{
			sNameCombined = sFirstName;

			if(sLastName != null)
			{
				sNameCombined += " " + sLastName;
			}
		}
		else if(sLastName != null)
		{
			sNameCombined = sLastName;
		}


		// Initialize UI Fields
		TextView tvUsername = (TextView)viewGroupBuddies.findViewById(R.id.textView_Username);
		TextView tvName = (TextView)viewGroupBuddies.findViewById(R.id.textView_firstAndLastName);
		TextView tvBAC = (TextView)viewGroupBuddies.findViewById(R.id.textView_BAC);
		TextView tvDrinkCount = (TextView)viewGroupBuddies.findViewById(R.id.textView_DrinkCount);

		// Set UI Fields
		tvUsername.setText(buddy.m_sBuddyUsername);
		tvName.setText("Firstname Lastname"); // sNameCombined);
		tvBAC.setText("0.00"); // TODO: replace with getBuddyBAC()
		tvDrinkCount.setText("0");	// TODO: replace with getBuddyDrinkCount()

		// Set Text Color of UI
		tvUsername.setTextColor(Color.WHITE);
		tvName.setTextColor(Color.WHITE);
		tvBAC.setTextColor(Color.WHITE);
		tvDrinkCount.setTextColor(Color.WHITE);

		return viewGroupBuddies; 
		
	} // getView()
	
}