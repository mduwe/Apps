package com.mathupyourlife.odesolver;

import android.app.VoiceInteractor;
import android.content.Context;
import android.widget.Toast;

import java.util.Vector;

/**
 * Created by mathias on 16.09.15.
 */
public class Mediator {
    public String _message;
    static int _counter;


    Mediator(String _message){
        this._message = _message;
    }
    Mediator(){
        this._message = "";
    }

    public void setMessage(CharSequence _message){
        this._message = _message.toString();
    }
    public void setMessage(String _message){
        this._message = _message;
    }

    public void showToasterMessage(Vector<String> _messageContainer, Context _context, int _duration){

        for (int index = 0; index <= _messageContainer.size()-1; index++){
            _message = _message + "\n" + _messageContainer.elementAt(index);
        }
        Toast toast = Toast.makeText(_context, _message, _duration);
        toast.show();
        _counter++;
    }

    public void showToasterMessage(String _message, Context _context, int _duration){

        Toast toast = Toast.makeText(_context, this._message, _duration);
        toast.show();
        _counter++;
    }

    public void showToasterMessage(Context _context, int _duration){

        Toast toast = Toast.makeText(_context, this._message, _duration);
        toast.show();
        _counter++;
    }
    //public void showErrorMessage(String message);


}