// Generated by view binder compiler. Do not edit!
package com.example.dearfutureme.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.dearfutureme.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityCreateCapsuleBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppCompatButton btnBack;

  @NonNull
  public final EditText dateFormat;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final TextView textView13;

  @NonNull
  public final TextView textView14;

  private ActivityCreateCapsuleBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppCompatButton btnBack, @NonNull EditText dateFormat,
      @NonNull ConstraintLayout main, @NonNull TextView textView13, @NonNull TextView textView14) {
    this.rootView = rootView;
    this.btnBack = btnBack;
    this.dateFormat = dateFormat;
    this.main = main;
    this.textView13 = textView13;
    this.textView14 = textView14;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityCreateCapsuleBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityCreateCapsuleBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_create_capsule, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityCreateCapsuleBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_back;
      AppCompatButton btnBack = ViewBindings.findChildViewById(rootView, id);
      if (btnBack == null) {
        break missingId;
      }

      id = R.id.dateFormat;
      EditText dateFormat = ViewBindings.findChildViewById(rootView, id);
      if (dateFormat == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.textView13;
      TextView textView13 = ViewBindings.findChildViewById(rootView, id);
      if (textView13 == null) {
        break missingId;
      }

      id = R.id.textView14;
      TextView textView14 = ViewBindings.findChildViewById(rootView, id);
      if (textView14 == null) {
        break missingId;
      }

      return new ActivityCreateCapsuleBinding((ConstraintLayout) rootView, btnBack, dateFormat,
          main, textView13, textView14);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
