package com.example.mathias.odesolver;

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

     public void showToasterMessage(Vector<String> _messageContainer, Context _context, int _duration){

       for (int index = 0; index <= _messageContainer.size()-1; index++){
            _message = _message + "\n" + _messageContainer.elementAt(index);
        }
        Toast toast = Toast.makeText(_context, _message, _duration);
        toast.show();
        _counter++;
    }
   //public void showErrorMessage(String message);


}
