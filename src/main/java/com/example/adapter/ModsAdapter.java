package com.example.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bean.ModRoot;
import com.example.ydshoa.R;

public class ModsAdapter extends BaseAdapter {
	private ViewHolder holder = null;
	private LayoutInflater inflater;
	private List<ViewHolder> lHolders = new ArrayList();
	private List<ModRoot> mods;
	private MyClickListener myClickListener = null;
	private int resource;
	private SharedPreferences sp;
	private Editor edit;

	public ModsAdapter() {
	}

	public ModsAdapter(Context paramContext, List<ModRoot> paramList,
			int paramInt) {
		this.mods = paramList;
		this.resource = paramInt;
		this.inflater = ((LayoutInflater) paramContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
		this.sp = paramContext.getSharedPreferences("ydbg", 0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.mods.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.mods.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int paramInt, View paramView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (paramView == null) {
			this.holder = new ViewHolder();
			paramView = this.inflater.inflate(this.resource, null);
			this.holder.id = ((TextView) paramView
					.findViewById(R.id.fuction_root_itemID));
			this.holder.name = ((TextView) paramView
					.findViewById(R.id.fuction_root_itemname));
			this.holder.name.setSelected(true);
			this.holder.querry = ((TextView) paramView
					.findViewById(R.id.fuction_root_itemquery));
			this.holder.add = ((TextView) paramView
					.findViewById(R.id.fuction_root_itemadd));
			this.holder.set = ((TextView) paramView
					.findViewById(R.id.fuction_root_itemset));
			this.holder.del = ((TextView) paramView
					.findViewById(R.id.fuction_root_itemdel));
			this.holder.prt = ((TextView) paramView
					.findViewById(R.id.fuction_root_itemprt));
			this.holder.out = ((TextView) paramView
					.findViewById(R.id.fuction_root_itemqut));
			this.holder.qty = ((TextView) paramView
					.findViewById(R.id.fuction_root_itemqty));
			this.holder.up = ((TextView) paramView
					.findViewById(R.id.fuction_root_itemup));
			this.holder.amt = ((TextView) paramView
					.findViewById(R.id.fuction_root_itemamt));
			this.holder.cst = ((TextView) paramView
					.findViewById(R.id.fuction_root_itemcst));
			this.holder.pr = ((TextView) paramView
					.findViewById(R.id.fuction_root_itempr));
			this.holder.prg = ((TextView) paramView
					.findViewById(R.id.fuction_root_itemprg));
			this.lHolders.add(this.holder);
			paramView.setTag(this.holder);

			ModRoot localModRoot = (ModRoot) this.mods.get(paramInt);
			this.holder.id.setText("" + localModRoot.getId());
			this.holder.name.setText("" + localModRoot.getModName());
			this.holder.querry.setText("" + localModRoot.isQuerry());
			this.holder.add.setText("" + localModRoot.isAdd());
			this.holder.set.setText("" + localModRoot.isSet());
			this.holder.del.setText("" + localModRoot.isSet());
			this.holder.prt.setText("" + localModRoot.isPrt());
			this.holder.out.setText("" + localModRoot.isQut());
			this.holder.qty.setText("" + localModRoot.isQty());
			this.holder.up.setText("" + localModRoot.isUp());
			this.holder.amt.setText("" + localModRoot.isAmt());
			this.holder.cst.setText("" + localModRoot.isCst());
			this.holder.pr.setText("" + localModRoot.isPr());
			this.holder.prg.setText("" + localModRoot.isPrg());
			this.holder.querry.setText("F");
			this.holder.add.setText("F");
			this.holder.set.setText("F");
			this.holder.del.setText("F");
			this.holder.prt.setText("F");
			this.holder.out.setText("F");
			this.holder.qty.setText("F");
			this.holder.up.setText("F");
			this.holder.amt.setText("F");
			this.holder.cst.setText("F");
			this.holder.pr.setText("F");
			this.holder.prg.setText("F");
			((ViewHolder) this.lHolders.get(paramInt)).querry.setTag(Boolean
					.valueOf(false));
			((ViewHolder) this.lHolders.get(paramInt)).add.setTag(Boolean
					.valueOf(false));
			((ViewHolder) this.lHolders.get(paramInt)).set.setTag(Boolean
					.valueOf(false));
			((ViewHolder) this.lHolders.get(paramInt)).del.setTag(Boolean
					.valueOf(false));
			((ViewHolder) this.lHolders.get(paramInt)).prt.setTag(Boolean
					.valueOf(false));
			((ViewHolder) this.lHolders.get(paramInt)).out.setTag(Boolean
					.valueOf(false));
			((ViewHolder) this.lHolders.get(paramInt)).qty.setTag(Boolean
					.valueOf(false));
			((ViewHolder) this.lHolders.get(paramInt)).up.setTag(Boolean
					.valueOf(false));
			((ViewHolder) this.lHolders.get(paramInt)).amt.setTag(Boolean
					.valueOf(false));
			((ViewHolder) this.lHolders.get(paramInt)).cst.setTag(Boolean
					.valueOf(false));
			((ViewHolder) this.lHolders.get(paramInt)).pr.setTag(Boolean
					.valueOf(false));
			((ViewHolder) this.lHolders.get(paramInt)).prg.setTag(Boolean
					.valueOf(false));
			edit = sp.edit();
			((ViewHolder) this.lHolders.get(paramInt)).querry
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View paramAnonymousView) {
							if (!((Boolean) ((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).querry.getTag())
									.booleanValue()) {
								((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
										.get(paramInt)).querry.setText("T");
								((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
										.get(paramInt)).querry.setTag(Boolean
										.valueOf(true));
								edit.putString(
										"querrytj",
										lHolders.get(paramInt).querry.getText()
												.toString()).commit();
								return;
							}
							((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).querry.setText("F");
							((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).querry.setTag(Boolean
									.valueOf(false));
							/*
							 * edit.putString( "querrytj",
							 * lHolders.get(paramInt).querry.getText()
							 * .toString()).commit();
							 */
						}
					});
			Log.e("LiNing", "*********"
					+ lHolders.get(paramInt).querry.getText().toString());
			// ((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
			// .get(paramInt)).querry.setText("F");
			// ((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
			// .get(paramInt)).querry.setTag(Boolean
			// .valueOf(false));
			edit.putString("querrytj",
					lHolders.get(paramInt).querry.getText().toString())
					.commit();
			((ViewHolder) this.lHolders.get(paramInt)).prg
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View paramAnonymousView) {
							if (!((Boolean) ((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).prg.getTag())
									.booleanValue()) {
								((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
										.get(paramInt)).prg.setText("T");
								((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
										.get(paramInt)).prg.setTag(Boolean
										.valueOf(true));
								edit.putString(
										"prgtj",
										lHolders.get(paramInt).prg.getText()
												.toString()).commit();
								return;
							}
							((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).prg.setText("F");
							((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).prg.setTag(Boolean
									.valueOf(false));
						}
					});
			edit.putString("prgtj",
					lHolders.get(paramInt).prg.getText().toString()).commit();
			((ViewHolder) this.lHolders.get(paramInt)).pr
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View paramAnonymousView) {
							if (!((Boolean) ((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).pr.getTag()).booleanValue()) {
								((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
										.get(paramInt)).pr.setText("T");
								((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
										.get(paramInt)).pr.setTag(Boolean
										.valueOf(true));
								edit.putString(
										"prtj",
										lHolders.get(paramInt).pr.getText()
												.toString()).commit();
								return;
							}
							((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).pr.setText("F");
							((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).pr.setTag(Boolean
									.valueOf(false));
						}
					});
			edit.putString("prtj",
					lHolders.get(paramInt).pr.getText().toString()).commit();
			((ViewHolder) this.lHolders.get(paramInt)).cst
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View paramAnonymousView) {
							if (!((Boolean) ((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).cst.getTag())
									.booleanValue()) {
								((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
										.get(paramInt)).cst.setText("T");
								((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
										.get(paramInt)).cst.setTag(Boolean
										.valueOf(true));
								edit.putString(
										"csttj",
										lHolders.get(paramInt).cst.getText()
												.toString()).commit();
								return;
							}
							((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).cst.setText("F");
							((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).cst.setTag(Boolean
									.valueOf(false));
						}
					});
			edit.putString("csttj",
					lHolders.get(paramInt).cst.getText().toString()).commit();
			((ViewHolder) this.lHolders.get(paramInt)).amt
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View paramAnonymousView) {
							if (!((Boolean) ((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).amt.getTag())
									.booleanValue()) {
								((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
										.get(paramInt)).amt.setText("T");
								((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
										.get(paramInt)).amt.setTag(Boolean
										.valueOf(true));
								edit.putString(
										"amttj",
										lHolders.get(paramInt).amt.getText()
												.toString()).commit();
								return;
							}
							((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).amt.setText("F");
							((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).amt.setTag(Boolean
									.valueOf(false));
						}
					});
			edit.putString("amttj",
					lHolders.get(paramInt).amt.getText().toString()).commit();
			((ViewHolder) this.lHolders.get(paramInt)).up
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View paramAnonymousView) {
							if (!((Boolean) ((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).up.getTag()).booleanValue()) {
								((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
										.get(paramInt)).up.setText("T");
								((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
										.get(paramInt)).up.setTag(Boolean
										.valueOf(true));
								edit.putString(
										"uptj",
										lHolders.get(paramInt).up.getText()
												.toString()).commit();
								return;
							}
							((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).up.setText("F");
							((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).up.setTag(Boolean
									.valueOf(false));
						}
					});
			edit.putString("uptj",
					lHolders.get(paramInt).up.getText().toString()).commit();
			((ViewHolder) this.lHolders.get(paramInt)).qty
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View paramAnonymousView) {
							if (!((Boolean) ((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).qty.getTag())
									.booleanValue()) {
								((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
										.get(paramInt)).qty.setText("T");
								((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
										.get(paramInt)).qty.setTag(Boolean
										.valueOf(true));
								edit.putString(
										"qtytj",
										lHolders.get(paramInt).qty.getText()
												.toString()).commit();
								return;
							}
							((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).qty.setText("F");
							((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).qty.setTag(Boolean
									.valueOf(false));
						}
					});
			edit.putString("qtytj",
					lHolders.get(paramInt).qty.getText().toString()).commit();
			((ViewHolder) this.lHolders.get(paramInt)).out
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View paramAnonymousView) {
							if (!((Boolean) ((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).out.getTag())
									.booleanValue()) {
								((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
										.get(paramInt)).out.setText("T");
								((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
										.get(paramInt)).out.setTag(Boolean
										.valueOf(true));
								edit.putString(
										"outtj",
										lHolders.get(paramInt).out.getText()
												.toString()).commit();
								return;
							}
							((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).out.setText("F");
							((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).out.setTag(Boolean
									.valueOf(false));
						}
					});
			edit.putString("outtj",
					lHolders.get(paramInt).out.getText().toString()).commit();
			((ViewHolder) this.lHolders.get(paramInt)).prt
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View paramAnonymousView) {
							if (!((Boolean) ((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).prt.getTag())
									.booleanValue()) {
								((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
										.get(paramInt)).prt.setText("T");
								((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
										.get(paramInt)).prt.setTag(Boolean
										.valueOf(true));
								edit.putString(
										"prttj",
										lHolders.get(paramInt).prt.getText()
												.toString()).commit();
								return;
							}
							((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).prt.setText("F");
							((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).prt.setTag(Boolean
									.valueOf(false));
						}
					});
			edit.putString("prttj",
					lHolders.get(paramInt).prt.getText().toString()).commit();
			((ViewHolder) this.lHolders.get(paramInt)).del
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View paramAnonymousView) {
							if (!((Boolean) ((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).del.getTag())
									.booleanValue()) {
								((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
										.get(paramInt)).del.setText("T");
								((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
										.get(paramInt)).del.setTag(Boolean
										.valueOf(true));
								edit.putString(
										"deltj",
										lHolders.get(paramInt).del.getText()
												.toString()).commit();
								return;
							}
							((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).del.setText("F");
							((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).del.setTag(Boolean
									.valueOf(false));
						}
					});
			edit.putString("deltj",
					lHolders.get(paramInt).del.getText().toString()).commit();
			((ViewHolder) this.lHolders.get(paramInt)).set
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View paramAnonymousView) {
							if (!((Boolean) ((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).set.getTag())
									.booleanValue()) {
								((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
										.get(paramInt)).set.setText("T");
								((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
										.get(paramInt)).set.setTag(Boolean
										.valueOf(true));
								edit.putString(
										"settj",
										lHolders.get(paramInt).set.getText()
												.toString()).commit();
								return;
							}
							((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).set.setText("F");
							((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).set.setTag(Boolean
									.valueOf(false));
						}
					});
			edit.putString("settj",
					lHolders.get(paramInt).set.getText().toString()).commit();
			((ViewHolder) this.lHolders.get(paramInt)).add
					.setOnClickListener(new View.OnClickListener() {
						public void onClick(View paramAnonymousView) {
							if (!((Boolean) ((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).add.getTag())
									.booleanValue()) {
								((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
										.get(paramInt)).add.setText("T");
								((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
										.get(paramInt)).add.setTag(Boolean
										.valueOf(true));
								edit.putString(
										"addtj",
										lHolders.get(paramInt).add.getText()
												.toString()).commit();
								return;
							}
							((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).add.setText("F");
							((ModsAdapter.ViewHolder) ModsAdapter.this.lHolders
									.get(paramInt)).add.setTag(Boolean
									.valueOf(false));
						}
					});
			edit.putString("addtj",
					lHolders.get(paramInt).add.getText().toString()).commit();
			notifyDataSetChanged();
			this.holder = ((ViewHolder) paramView.getTag());
		}
		return paramView;
	}

	private class MyClickListener implements View.OnClickListener {
		private int position;

		public MyClickListener(int i) {
			this.position = i;
		}

		public void onClick(View paramView) {
		}
	}

	private class ViewHolder {
		public TextView add;
		public TextView amt;
		public TextView cst;
		public TextView del;
		public TextView id;
		public TextView name;
		public TextView out;
		public TextView pr;
		public TextView prg;
		public TextView prt;
		public TextView qty;
		public TextView querry;
		public TextView set;
		public TextView up;

		private ViewHolder() {
		}
	}
}
