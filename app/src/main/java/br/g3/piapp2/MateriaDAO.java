package br.g3.piapp2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MateriaDAO {
    private Context context;
    public MateriaDAO(Context context) {
        this.context = context;
    }
    public ArrayList<Materia> busca() {
        MateriaDBHelper dbHelper = new MateriaDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Materia> materias = new ArrayList<>();
        String command = String.format(
                Locale.getDefault(),
                "SELECT * FROM %s",
                HelpDeskContract.MateriaContract.TABLE_NAME
        );
        Cursor cursor = db.rawQuery(command, null);
        while (cursor.moveToNext()) {
            int idMateria = cursor.getInt(
                    cursor.getColumnIndex(String.format(Locale.getDefault(),
                        "%s.%s",
                        HelpDeskContract.MateriaContract.TABLE_NAME,
                        HelpDeskContract.MateriaContract.COLUMN_NAME_ID)
                    )
            );
            String nome =
                    cursor.getString(cursor.getColumnIndex(
                            String.format(
                                    Locale.getDefault(),
                                    "%s.%s",
                                    HelpDeskContract.MateriaContract.TABLE_NAME,
                                    HelpDeskContract.MateriaContract.COLUMN_NAME_NOME
                            )
                    ));
            String horario =
                    cursor.getString(cursor.getColumnIndex(
                            String.format(
                                    Locale.getDefault(),
                                    "%s.%s",
                                    HelpDeskContract.MateriaContract.TABLE_NAME,
                                    HelpDeskContract.MateriaContract.COLUMN_NAME_HORARIO
                            )
                    ));
            int iconId = cursor.getInt(
                    cursor.getColumnIndex(
                            String.format(
                                    Locale.getDefault(),

                                    "%s.%s",
                                    HelpDeskContract.MateriaContract.TABLE_NAME,
                                    HelpDeskContract.MateriaContract.COLUMN_NAME_ICON_ID

                            )
                    )
            );
            Materia materia = new Materia();
            materia.setId(idMateria);
            materia.setNome(nome);
            //materia.setHorario(horario);
            materia.setIconId(iconId);
            materias.add(materia);
        }
        cursor.close();
        db.close();
        dbHelper.close();
        return materias;
    }
}
