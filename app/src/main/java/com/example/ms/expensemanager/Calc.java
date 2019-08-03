package com.example.ms.expensemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


public class Calc extends AppCompatActivity {
    TextView tvOne,tvTwo,tvThree,tvFour,tvFive,tvSix,tvSeven,tvEight,tvNine,tvZero,tvDot,tvResult,tvExpression,tvMul,tvDivide,tvPlus,tvMinus,tvOpen,tvClose,tvClear,tvEquals;
    ImageView tvBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc);
        tvOne = (TextView) findViewById(R.id.tvOne);
        tvTwo = (TextView) findViewById(R.id.tvTwo);
        tvThree = (TextView) findViewById(R.id.tvThree);
        tvFour = (TextView) findViewById(R.id.tvFour);
        tvFive = (TextView) findViewById(R.id.tvFive);
        tvSix = (TextView) findViewById(R.id.tvSix);
        tvSeven = (TextView) findViewById(R.id.tvSeven);
        tvEight = (TextView) findViewById(R.id.tvEight);
        tvNine = (TextView) findViewById(R.id.tvNine);
        tvZero= (TextView) findViewById(R.id.tvZero);
        tvDot = (TextView) findViewById(R.id.tvDot);

tvResult=(TextView)findViewById(R.id.tvResult);

tvPlus=(TextView)findViewById(R.id.tvPlus);
tvMinus=(TextView)findViewById(R.id.tvMinus);
tvDivide=(TextView)findViewById(R.id.tvDivide);
tvMul=(TextView)findViewById(R.id.tvMul);
tvOpen=(TextView)findViewById(R.id.tvOpen);
tvClose=(TextView)findViewById(R.id.tvClose);
tvClear=(TextView)findViewById(R.id.tvClear);
tvBack=(ImageView)findViewById(R.id.tvBack);
tvEquals=(TextView)findViewById(R.id.tvEquals);
tvExpression=(TextView)findViewById(R.id.tvExpression);
tvPlus.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        appendOnExpresstion("+", false);
    }
});
tvMinus.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        appendOnExpresstion("-", false);
    }
});

tvMul.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        appendOnExpresstion("*", false);
    }
});
tvDivide.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        appendOnExpresstion("/", false);
    }
});
tvOpen.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        appendOnExpresstion("(", false);
    }
});
tvClose.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        appendOnExpresstion(")", false);
    }
});
tvClear.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        tvExpression.setText("");
        tvResult.setText ("");
    }
});
tvBack.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String string = tvExpression.getText().toString();
        if(!string.equals("")){
            int len=string.length();
            tvExpression.setText(string.substring(0,len-1));
        }
        tvResult.setText("");
    }
});
tvEquals.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        try{
           Expression expression = new ExpressionBuilder(tvExpression.getText().toString()).build();
            double  result = expression.evaluate();
           long longResult =(long) result;
            if(result == (double)longResult)
                tvResult.setText(String.valueOf(longResult));
            else
                tvResult.setText(String.valueOf(result));

        }catch (Exception e){
            Log.d("Exception"," message : " + e.getMessage() );
        }
    }
});




        tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpresstion("1", true);
            }
        });
        tvTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpresstion("2", true);
            }
        });
        tvThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpresstion("3", true);
            }
        });
        tvFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpresstion("4", true);
            }
        });
        tvFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpresstion("5", true);
            }
        });
        tvSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpresstion("6", true);
            }
        });
        tvSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpresstion("7", true);
            }
        });
        tvEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpresstion("8", true);
            }
        });
        tvNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpresstion("9", true);
            }
        });
        tvZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpresstion("0", true);
            }
        });
        tvDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendOnExpresstion(".", true);
            }
        });

    }


    public void  appendOnExpresstion(String string, Boolean canClear) {
        tvResult=(TextView)findViewById(R.id.tvResult);
        tvExpression=(TextView)findViewById(R.id.tvExpression);

        if(!tvResult.getText().equals("")){
            tvExpression.setText("");
        }

        if (canClear) {
            tvResult.setText("");
            tvExpression.append(string);
        } else {
            tvExpression.append(tvResult.getText());
            tvExpression.append(string);
            tvResult.setText("");
        }
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(Calc.this,Tools.class);
        startActivity(i);
        Calc.this.finish();
        overridePendingTransition(0, 0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calc, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.addinc:
            //add the function to perform here

            Intent intent=new Intent(Calc.this,Income.class);
            String result=tvResult.getText().toString();
            intent.putExtra("result",result);
            startActivity(intent);
            Calc.this.finish();
            overridePendingTransition(0, 0);
            return(true);
        case R.id.addexp:
            //add the function to perform here

            Intent intente=new Intent(Calc.this,Expense.class);
            String resulte=tvResult.getText().toString();
            intente.putExtra("result",resulte);
            startActivity(intente);
            Calc.this.finish();
            overridePendingTransition(0, 0);
            return(true);


    }
        return(super.onOptionsItemSelected(item));
    }
}
