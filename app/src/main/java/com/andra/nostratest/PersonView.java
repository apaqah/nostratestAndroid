package com.andra.nostratest;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Andra on 7/29/2018.
 */

@EViewGroup(R.layout.list_item)
public class PersonView extends RelativeLayout {

    @ViewById
    TextView txtName;
    @ViewById
    TextView txtPhone;
    @ViewById
    TextView txtEmail;

    private Contact contact;
    private OnActionListener onActionListener;

    public PersonView(Context context) {
        super(context);
    }

    @Click(R.id.delete)
    protected void deleteContact() {
        if (onActionListener != null) {
            onActionListener.onDelete(this);
        }
    }

    @Click(R.id.edit)
    protected void editContact() {
        if (onActionListener != null) {
            onActionListener.onEdit(this);
        }
    }

    public void setDatas(Contact contacts) {
        if (contacts != null) {
            contact = contacts;
            updateUI();
        }
    }

    public Contact getDatas(){return contact;}

    private void updateUI() {
        txtName.setText(contact.getName());
        txtEmail.setText(contact.getEmail());
        txtPhone.setText(contact.getPhone());
    }

    public void setOnActionListener(OnActionListener listener){
        onActionListener = listener;
    }

    public interface OnActionListener {
        void onEdit(PersonView personView);

        void onDelete(PersonView personView);
    }
}
