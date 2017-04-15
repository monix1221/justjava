/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava; 
 */
package com.example.monika.justjava;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int quantity=2;
    public void submitOrder(View view) {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox_view);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox_view);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        EditText nameText = (EditText) findViewById(R.id.name);
        String customerName = nameText.getText().toString();


       // Log.v("MainActivity", "Has chocolate: " + hasChocolate);
       // Log.v("MainActivity", "The price is: " + price);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(price, hasWhippedCream, hasChocolate, customerName));
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for "+customerName);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


        displayMessage(createOrderSummary(price, hasWhippedCream, hasChocolate, customerName));



    }

    public void displayMessage(String message){
        TextView orderSummaryTextView=(TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    public void increment(View view) {

        if(quantity<100){
        quantity=quantity+1;
        } else {

            Toast.makeText(this, getString(R.string.toast_cant_order_more), Toast.LENGTH_SHORT).show();
            return;
        }
        display(quantity);
    }

    public void decrement(View view) {

        if (quantity == 1) {
            Toast.makeText(this, getString(R.string.toast_cant_order_less), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     *
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate){

        int basePrice = 5;

        if(addWhippedCream){
            basePrice = basePrice + 1;
        }

        if(addChocolate){
            basePrice = basePrice + 2;
        }


        return quantity*basePrice;

    }
    /**
     *
     * @param price of the order
     * @return text summary
     */
    private String createOrderSummary(int price, boolean hasCream, boolean hasChocolate, String name){


        String priceMessage= getString(R.string.order_summary_name, name);
        priceMessage+="\n"+ getString(R.string.add_whipped_cream)+" "+hasCream;
        priceMessage+="\n"+ getString(R.string.add_chocolate)+" "+hasChocolate;
        priceMessage+="\n"+ getString(R.string.quantity)+" "+quantity;
        priceMessage+="\n"+ getString(R.string.total_price, NumberFormat.getCurrencyInstance().format(price));
        priceMessage+="\n"+ getString(R.string.thank_you);

        return priceMessage;
    }











}