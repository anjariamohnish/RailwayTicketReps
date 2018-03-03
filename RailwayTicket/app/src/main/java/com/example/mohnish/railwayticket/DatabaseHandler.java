package com.example.mohnish.railwayticket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.mohnish.railwayticket.SupportFiles.EncryptionHelper;
import com.example.mohnish.railwayticket.SupportFiles.ticket;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 10/02/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FileManager";
    private static final String TABLE_FILEDATA = "FileData";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATA = "data";
    private static final SimpleDateFormat rawFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static final SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
    private Context context;

    public DatabaseHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_FILEDATA + "(" + KEY_NAME + " TEXT," + KEY_DATA + " TEXT )";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FILEDATA);

        onCreate(sqLiteDatabase);
    }

    void AddFile(String filename, String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, filename);
        values.put(KEY_DATA, data);
        db.insert(TABLE_FILEDATA, null, values);
        db.close();

    }

    void DelFile(String filename) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FILEDATA, KEY_NAME + " = ?",
                new String[]{String.valueOf(filename)});
        db.close();
    }

    List<ticket> GetFile() throws Exception {
        List<ticket> tickets = new ArrayList<ticket>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FILEDATA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String returnStatus = "Single";
                String classType = "II";
                String[] qrData = EncryptionHelper.decipher(
                        cursor.getString(1).substring(cursor.getString(1).length() - 12, cursor.getString(1).length()),
                        cursor.getString(1).substring(0, cursor.getString(1).length() - 12
                        )).split(";");
                RemovePropertyName(qrData);


                if (!qrData[3].equals("0")) {
                    returnStatus = "Return";
                }
                if (!qrData[2].equals("Second Class")) {
                    classType = "I";
                }
                tickets.add(new ticket(
                        format.format(rawFormatter.parse(qrData[5])),
                        "NIL",
                        qrData[0],
                        "NIL",
                        qrData[1],
                        returnStatus,
                        classType,
                        "1",
                        format.format(rawFormatter.parse(qrData[6])),
                        //TextToImageEncode(cursor.getString(1))
                        TextToImageEncode(cursor.getString(1))
                ));


            } while (cursor.moveToNext());
        }

        return tickets;
    }

    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    500, 500, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        context.getResources().getColor(R.color.QRCodeBlackColor) : context.getResources().getColor(R.color.QRCodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    private void RemovePropertyName(String[] data) {
        for (int i = 0; i < data.length; i++) {
            data[i] = data[i].substring(data[i].indexOf("=") + 1);
        }
    }
}
