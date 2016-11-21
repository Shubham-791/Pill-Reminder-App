package pillapp.ViewController;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.CheckBox;
import com.example.vyom.pillapp.R;


public class CustomCheckBox extends CheckBox {

    public CustomCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setChecked(boolean t){
        if(t) {
            // checkbox_background is blue
            this.setBackgroundResource(R.drawable.checkbox_background);
            this.setTextColor(Color.WHITE);
        } else {
            this.setBackgroundColor(Color.TRANSPARENT);
            this.setTextColor(Color.BLACK);
        }
        super.setChecked(t);
    }
}