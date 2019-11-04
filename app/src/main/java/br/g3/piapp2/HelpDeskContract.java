package br.g3.piapp2;

import android.provider.BaseColumns;

import java.util.Date;
import java.util.Locale;

public class HelpDeskContract {
    private HelpDeskContract(){
    }

    private static Materia materia;
    static {
        materia = new Materia();
        materia.setId(1);
        materia.setNome("Projeto Integrado");
        //materia.setHorario("19h10");
        materia.setIconId(R.drawable.ic_launcher_background);
    }

    public static class FaltaContract implements BaseColumns{
        public static final String TABLE_NAME = "tb_falta";
        public static final String COLUMN_NAME_ID = "id_falta";
        public static final String COLUMN_NAME_DATA = "data";
    }

    public static class AlunoContract implements BaseColumns{
        public static final String TABLE_NAME = "tb_aluno";
        public static final String COLUMN_NAME_ID = "id_aluno";
        public static final String COLUMN_NAME_NOME = "nome";
        public static final String COLUMN_NAME_RA = "ra";
        public static final String COLUMN_NAME_SENHA = "senha";
    }

    public static class ProfessorContract implements BaseColumns{
        public static final String TABLE_NAME = "tb_professor";
        public static final String COLUMN_NAME_ID = "id_professor";
        public static final String COLUMN_NAME_NOME = "nome";
        public static final String COLUMN_NAME_RA = "ra";
        public static final String COLUMN_NAME_SENHA = "senha";
    }

    public static class MateriaContract implements BaseColumns {
        public static final String TABLE_NAME = "tb_materia";
        public static final String COLUMN_NAME_ID = "id_materia";
        public static final String COLUMN_NAME_NOME = "nome";
        public static final String COLUMN_NAME_HORARIO = "horario";
        public static final String COLUMN_NAME_ICON_ID = "icon_id";

        public static final String DROP_TABLE = String.format("DROP TABLE %s", MateriaContract.TABLE_NAME);
    }

    public static String createTableMateria (){
        return String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s INTEGER);",
                MateriaContract.TABLE_NAME,
                MateriaContract.COLUMN_NAME_ID,
                MateriaContract.COLUMN_NAME_NOME,
                MateriaContract.COLUMN_NAME_HORARIO,
                MateriaContract.COLUMN_NAME_ICON_ID);
    }

    public static String insertMateria (){
        String template = "INSERT INTO %s (%s, %s, %s, %s) VALUES (%d, '%s', '%s', %d);";
        StringBuilder sb = new StringBuilder ("");
        sb.append(
                String.format(
                        Locale.getDefault(),
                        template,
                        MateriaContract.TABLE_NAME,
                        MateriaContract.COLUMN_NAME_ID,
                        MateriaContract.COLUMN_NAME_NOME,
                        MateriaContract.COLUMN_NAME_HORARIO,
                        MateriaContract.COLUMN_NAME_ICON_ID,
                        materia.getId(),
                        materia.getNome(),
                        //materia.getHorario(),
                        materia.getIconId()

                )
        );
        return sb.toString();
    }
}
