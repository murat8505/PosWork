package com.RecieptPrints;

import android.content.Context;

import com.AlertDialogs.ChangeAmountDialog;
import com.AlertDialogs.ReceiptPrintPrompt;
import com.CustomControls.ShowToastMessage;
import com.Fragments.MaintFragmentOtherSetting;
import com.PosInterfaces.PrefrenceKeyConst;
import com.SetupPrinter.BasePR;
import com.SetupPrinter.PrinterCallBack;
import com.SetupPrinter.UsbPR;
import com.Socket.ConvertStringOfJson;
import com.Utils.GlobalApplication;
import com.Utils.MyPreferences;
import com.Utils.MyStringFormat;
import com.Utils.StartAndroidActivity;
import com.Utils.Variables;
import com.posimplicity.HomeActivity;

public class GoForPrint implements PrefrenceKeyConst {

    private Context mContext;
    private HomeActivity localInsatance;
    private GlobalApplication gApp;
    private boolean isActivityNeededToFinish;
    private boolean isPrintPromptNeeded;
    private boolean isReceiptNeededToPrint;
    private boolean isDuplicateReceiptNeededToPrint;
    private boolean isCustomOptionNeedToPrint;

    public static final int CASH_FRAGMENT = 0;
    public static final int CREDIT_FRAGMENT = 1;
    public static final int CHECQUE_FRAGMENT = 2;
    public static final int REWARDS_FRAGMENT = 3;

    public int numdersOfReciepts = 0x01;
    private int paymentMode = 0x00;


    public GoForPrint(Context mContext, int paymentMode, boolean activityNeedToFinish) {
        this.mContext = mContext;
        this.paymentMode = paymentMode;
        this.localInsatance = HomeActivity.localInstance;
        this.gApp = GlobalApplication.getInstance();
        this.isActivityNeededToFinish = activityNeedToFinish;
        this.isPrintPromptNeeded = MyPreferences.getBooleanPrefrences(IS_RECEIPT_PROMPT_ON_PS, mContext);
        this.isDuplicateReceiptNeededToPrint = MyPreferences.getBooleanPrefrences(IS_DUPLICATE_RECIEPT_ON_PS, mContext);
        this.isCustomOptionNeedToPrint = MyPreferences.getBooleanPrefrences(IS_CUSTOM_OPTION_PRINT_ON_OFF, mContext);
    }

    public void onExectue() {

        new ConvertStringOfJson(mContext).onFullPayment();

        if (isDuplicateReceiptNeededToPrint) {
            numdersOfReciepts++;
        }

        if (isPrintPromptNeeded)
            new ReceiptPrintPrompt(this, mContext, paymentMode).showReceiptPrintPrompt();

        else {
            if (paymentMode == CREDIT_FRAGMENT)
                numdersOfReciepts++;
            onPreExecute(true);
        }
    }


    public void onPreExecute(boolean bool) {

        isReceiptNeededToPrint = bool;
        boolean isAnyConnectionAvailable = false;

        // Printing Kitchen Receipt ; ------------------ Start

        if (PrintSettings.isAbleToPrintKitchenReceiptThroughBluetooth(mContext)) {
            for (int index = 0; index < 1; index++) {
                if (MyPreferences.getLongPreference(POS_STORE_TYPE, mContext) == MaintFragmentOtherSetting.QUICK_ && isReceiptNeededToPrint)
                    KitchenReceipt.onPrintKitchenReciept(mContext, HomeActivity.localInstance, gApp.getmBasePrinterBT());
            }
        }

        if (PrintSettings.isAbleToPrintKitchenReceiptThroughUsb(mContext)) {
            new UsbPR(mContext, new PrinterCallBack() {

                @Override
                public void onStop() {
                }

                @Override
                public void onStarted(BasePR printerCmmdO) {
                    for (int index = 0; index < 1; index++) {
                        if (MyPreferences.getLongPreference(POS_STORE_TYPE, mContext) == MaintFragmentOtherSetting.QUICK_ && isReceiptNeededToPrint)
                            KitchenReceipt.onPrintKitchenReciept(mContext, HomeActivity.localInstance, printerCmmdO);
                    }
                }
            });
        }

        if (PrintSettings.canPrintWifiSlip(mContext)) {
            for (int index = 0; index < 1; index++) {
                if (MyPreferences.getLongPreference(POS_STORE_TYPE, mContext) == MaintFragmentOtherSetting.QUICK_ && isReceiptNeededToPrint)
                    KitchenReceipt.onPrintKitchenReciept(mContext, HomeActivity.localInstance, gApp.getmBasePrinterWF());
            }
        }

        // Printing Kitchen Receipt ; ------------------ End


        if (PrintSettings.isAbleToPrintCustomerReceiptThroughBluetooth(mContext)) {
            BasePR printerCmmdO = gApp.getmBasePrinterBT();
            PrintReceiptCustomer pReciept = new PrintReceiptCustomer(mContext);
            for (int index = 0; index < numdersOfReciepts; index++) {
                if (isReceiptNeededToPrint) {
                    if (numdersOfReciepts == 2 && Variables.paymentByCC)
                        pReciept.onPrintRecieptCustomer(printerCmmdO, !isCustomOptionNeedToPrint);

                    else if (numdersOfReciepts > 1 && index == numdersOfReciepts - 1)
                        pReciept.onPrintRecieptCustomer(printerCmmdO, true);

                    else
                        pReciept.onPrintRecieptCustomer(printerCmmdO, !isCustomOptionNeedToPrint);
                }
            }
        }

        if (PrintSettings.isAbleToPrintCustomerReceiptThroughUsb(mContext)) {
            isAnyConnectionAvailable = true;
            new UsbPR(mContext, new PrinterCallBack() {

                @Override
                public void onStop() {
                    onPostExecute();
                }

                @Override
                public void onStarted(BasePR printerCmmdO) {
                    PrintReceiptCustomer pReciept = new PrintReceiptCustomer(mContext);
                    for (int index = 0; index < numdersOfReciepts; index++) {
                        if (isReceiptNeededToPrint) {
                            if (numdersOfReciepts == 2 && Variables.paymentByCC)
                                pReciept.onPrintRecieptCustomer(printerCmmdO, !isCustomOptionNeedToPrint);

                            else if (numdersOfReciepts > 1 && index == numdersOfReciepts - 1)
                                pReciept.onPrintRecieptCustomer(printerCmmdO, true);

                            else
                                pReciept.onPrintRecieptCustomer(printerCmmdO, !isCustomOptionNeedToPrint);
                        }
                    }
                    onPostExecute();
                }
            }).onStart();
        }

        if (!isAnyConnectionAvailable)
            onPostExecute();
    }

    public void onPostExecute() {

        boolean anyDrawerOptionIsTrue = false;

        switch (paymentMode) {

            case CASH_FRAGMENT:
                anyDrawerOptionIsTrue = MyPreferences.getBooleanPreferencesWithDefalutTrue(DRAWER_CASH, mContext);
                localInsatance.changeAmtTv.setText(MyStringFormat.onFormat(Variables.changeAmt));
                localInsatance.cashTv.setText(MyStringFormat.onFormat(Variables.cashAmount));
                break;

            case CHECQUE_FRAGMENT:
                anyDrawerOptionIsTrue = MyPreferences.getBooleanPrefrences(DRAWER_CHECK, mContext);
                break;

            case CREDIT_FRAGMENT:
                anyDrawerOptionIsTrue = MyPreferences.getBooleanPrefrences(DRAWER_CC, mContext);
                ShowToastMessage.showApprovedToast(mContext);

            case REWARDS_FRAGMENT:
                break;

            default:
                break;
        }

        if (anyDrawerOptionIsTrue) {
            if (PrintSettings.isAbleToPrintCustomerReceiptThroughUsb(mContext)) {
                new UsbPR(mContext, new PrinterCallBack() {

                    @Override
                    public void onStop() {
                    }

                    @Override
                    public void onStarted(BasePR printerCmmdO) {
                        PrintExtraReceipt.onOpenCashDrawer(printerCmmdO);
                    }

                }).onStart();
            }

            if (PrintSettings.isAbleToPrintCustomerReceiptThroughBluetooth(mContext)) {
                PrintExtraReceipt.onOpenCashDrawer(gApp.getmBasePrinterBT());
            }
        }

        if (Variables.changeAmt > 0)
            ChangeAmountDialog.showChangeAmount(mContext, localInsatance, Variables.changeAmt);
        else {
            localInsatance.resetAllData(mContext, 0);
            if (isActivityNeededToFinish)
                StartAndroidActivity.onActivityStart(false, mContext, HomeActivity.class);
        }
    }
}
