package com.satpay.ecoshop.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.satpay.ecoshop.R;

public class PaymentFragment extends Fragment {
    TextView txtTotalPrice;
    EditText edtCardNum, edtCVV2, edtYear, edtMonth, edtEpass;
    Button btnCancel, btnCheckout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        Double totalPrice = getArguments().getDouble("total_price", 0);


        txtTotalPrice = view.findViewById(R.id.txt_total_amount_price);
        edtCardNum = view.findViewById(R.id.edt_card_num);
        edtCVV2 = view.findViewById(R.id.edt_cvv2);
        edtYear = view.findViewById(R.id.edt_year);
        edtMonth = view.findViewById(R.id.edt_month);
        edtEpass = view.findViewById(R.id.edt_epass);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnCheckout = view.findViewById(R.id.btn_check_out);


        txtTotalPrice.setText(String.format("$%s", totalPrice));

        btnCancel.setOnClickListener(view1 -> {
            getParentFragmentManager().popBackStack();
        });

        btnCheckout.setOnClickListener(view12 -> {
            if (edtCardNum.getText().toString().isEmpty() ||
                    edtCVV2.getText().toString().isEmpty() ||
                    edtYear.getText().toString().isEmpty() ||
                    edtMonth.getText().toString().isEmpty() ||
                    edtEpass.getText().toString().isEmpty()) {
                Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.fill_fields), Toast.LENGTH_LONG).show();
            }
//            else if (edtCardNum.getText().length() < 12) {
//                Toast.makeText(requireActivity(), requireActivity().getResources().getString(R.string.not_correct_card_num), Toast.LENGTH_LONG).show();
//            }
            else {
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.payment_dialog);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

                TextView txtTotal, txtCardNum;
                Button btnComplete;
                txtTotal = dialog.findViewById(R.id.txt_total_amount_price);
                txtCardNum = dialog.findViewById(R.id.txt_card_number);
                btnComplete = dialog.findViewById(R.id.btn_complete);

                txtTotal.setText(txtTotalPrice.getText());
                txtCardNum.setText(edtCardNum.getText().toString());
                btnComplete.setOnClickListener(view13 -> {
                    dialog.dismiss();
                });

                dialog.show();
            }
        });

        TextWatcher t = new TextWatcher() {

            private static final int TOTAL_SYMBOLS = 19;
            private static final int TOTAL_DIGITS = 16;
            private static final int DIVIDER_MODULO = 5;
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1;
            private static final char DIVIDER = '-';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(0, s.length(), buildCorrectString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }
            }

            private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
                boolean isCorrect = s.length() <= totalSymbols;
                for (int i = 0; i < s.length(); i++) {
                    if (i > 0 && (i + 1) % dividerModulo == 0) {
                        isCorrect &= divider == s.charAt(i);
                    } else {
                        isCorrect &= Character.isDigit(s.charAt(i));
                    }
                }
                return isCorrect;
            }

            private String buildCorrectString(char[] digits, int dividerPosition, char divider) {
                final StringBuilder formatted = new StringBuilder();

                for (int i = 0; i < digits.length; i++) {
                    if (digits[i] != 0) {
                        formatted.append(digits[i]);
                        if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                            formatted.append(divider);
                        }
                    }
                }

                return formatted.toString();
            }

            private char[] getDigitArray(final Editable s, final int size) {
                char[] digits = new char[size];
                int index = 0;
                for (int i = 0; i < s.length() && index < size; i++) {
                    char current = s.charAt(i);
                    if (Character.isDigit(current)) {
                        digits[index] = current;
                        index++;
                    }
                }
                return digits;
            }
        };
        edtCardNum.removeTextChangedListener(t);
        edtCardNum.addTextChangedListener(t);

        return view;
    }

}