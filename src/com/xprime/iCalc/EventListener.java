package com.xprime.iCalc;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.xprime.OptionalService.R;

/**
 * Created by IntelliJ IDEA.
 * User: izahid
 * Date: May 28, 2010
 * Time: 1:23:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventListener implements View.OnKeyListener, View.OnClickListener, View.OnLongClickListener {
    private iCalc _this;
    private DecimalFormat numberFormat;

    public EventListener(iCalc parent) {
        super();
        _this = parent;
        numberFormat = new DecimalFormat();
    }

    public void onClick(View view) {
        if (view instanceof Button) {
            String text = ((Button)view).getText().toString();
            int id = view.getId();
            switch (id) {
                case R.id.del:
                    if (_this.getOper() == null) {
                        _this.setPre(null);
                    } else {
                        _this.setPost(null);
                    }
                    _this.setText("0");
                    break;
                case R.id.adel:
                    clearAll();
                    break;
                case R.id.plusminus: {
                    String number = null;
                    if (_this.getOper() == null) {
                        number = _this.getPre();
                    } else {
                        number = _this.getPost();
                    }
                    if (number == null) {
                        number = "0";
                    }
                    Double result = Double.valueOf(number) * -1;
                    if (_this.getOper() == null) {
                        _this.setPre(String.valueOf(result));
                    } else {
                        _this.setPost(String.valueOf(result));
                    }
                    formalizeText(String.valueOf(result));
                }
                    break;
                case R.id.perc: {
                    if (_this.getOper() == null) {
                        clearAll();
                        break;
                    }
                    Double perc = null;
                    if (_this.getPost() != null) {
                        perc = Double.valueOf(_this.getPost());
                    } else {
                        perc = Double.valueOf(_this.getPre());
                    }
                    Double result = perc/100 * Double.valueOf(_this.getPre());
                    String s = String.valueOf(result);
                    _this.setPost(s);
                    formalizeText(s);
                }
                    break;
                case R.id.div:
                case R.id.mul:
                case R.id.minus:
                case R.id.plus:
                    if (_this.getPre() == null) {
                        _this.setPre("0");
                    } else if (_this.getPost() != null) {
                        Double result = calculateResult();
                        _this.setPre(String.valueOf(result));
                    }
                    formalizeText(String.valueOf(_this.getPre()), text);
                    _this.setOper(text.charAt(0));
                    break;
                case R.id.dot: {
                    String number = "";
                    if (_this.getOper() == null) {
                        number = _this.getPre();
                    } else {
                        number = _this.getPost();
                    }
                    if (number == null) {
                        break;
                    }
                    if (number.indexOf(".") < 0) {
                        number = number.concat(".");
                    }
                    if (_this.getOper() == null) {
                        _this.setPre(number);
                    } else {
                        _this.setPost(number);
                    }
                    formalizeText(number);
                }
                    break;
                case R.id.equal:
                    String temp = _this.getPre();
                    Double result = calculateResult();
                    if (result != null) {
                        String s = String.valueOf(result);
                        if (s.toLowerCase().indexOf('e') > 0) {
                            s = new BigDecimal(result).toPlainString();
                        }
                        formalizeText(s);
                    }
                    break;
                default: {
                    String number = null;
                    if (_this.getOper() == null) {
                        number = _this.getPre();
                    } else {
                        number = _this.getPost();
                    }
                    if (number == null) {
                        number = "";
                    }
                    if (number.length() >= 12) {
                        break;
                    }
                    number = number.concat(text);
                    if (_this.getOper() == null) {
                        _this.setPre(number);
                    } else {
                        _this.setPost(number);
                    }
                    formalizeText(number);
                }
                    break;
            }
        }
    }
    
    private Double calculateResult() {
        Double result = null;
        if (_this.getPre() != null && _this.getPost() != null && _this.getOper() != null) {
            Double regPre  = Double.valueOf(_this.getPre());
            Double regPost = Double.valueOf(_this.getPost());
            char oper = '?';
            if (iCalc.isPlus(_this.getOper())) {
                result = regPre + regPost;
                oper = '+';
            } else if (iCalc.isMinus(_this.getOper())) {
                result = regPre - regPost;
                oper = '-';
            } else if (iCalc.isMultiply(_this.getOper())) {
                result = regPre * regPost;
                oper = '*';
            } else if (iCalc.isDivide(_this.getOper())) {
                result = regPre / regPost;
                oper = '/';
            }
            iCalc.log("[" + regPre + "] " + oper + " [" + regPost + "] = " + result);
        }
        clearAll();
        return result;
    }

    private void clearAll() {
        _this.setPre(null);
        _this.setPost(null);
        _this.setOper(null);
        _this.setText("0");
    }

    private void formalizeText(String number) {
        formalizeText(number, "");
    }

    private void formalizeText(String number, String operand) {
        String intPart = number;
        String fracPart = "";
        int index = number.indexOf('.');
        if (index > 0) {
            intPart = number.substring(0, index);
            fracPart = number.substring(index);
        }
        String display = numberFormat.format(Double.valueOf(intPart)) + fracPart + operand;
        _this.setText(display);
    }

    public boolean onLongClick(View view) {
        return false;
    }

    private static final char[] EQUAL = {'='};

    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        int action = keyEvent.getAction();

        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            return true;
        }

        //Work-around for spurious key event from IME, bug #1639445
        if (action == KeyEvent.ACTION_MULTIPLE && keyCode == KeyEvent.KEYCODE_UNKNOWN) {
            return true; // eat it
        }

        iCalc.log("KEY " + keyCode + "; " + action);

        if (keyEvent.getMatch(EQUAL, keyEvent.getMetaState()) == '=') {
            if (action == KeyEvent.ACTION_UP) {
                //equals
            }
            return true;
        }

        if (keyCode != KeyEvent.KEYCODE_DPAD_CENTER &&
            keyCode != KeyEvent.KEYCODE_DPAD_UP &&
            keyCode != KeyEvent.KEYCODE_DPAD_DOWN &&
            keyCode != KeyEvent.KEYCODE_ENTER) {
            return false;
        }

        return true;
    }
}
