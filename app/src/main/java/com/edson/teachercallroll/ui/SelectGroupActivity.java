package com.edson.teachercallroll.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.edson.teachercallroll.R;
import com.edson.teachercallroll.adapterholder.GroupAdapter;
import com.edson.teachercallroll.adapterholder.ModuleAdapter;
import com.edson.teachercallroll.adapterholder.SemestreAdapter;
import com.edson.teachercallroll.adapterholder.formations.FormationAdapter;
import com.edson.teachercallroll.model.FormationDto;
import com.edson.teachercallroll.model.GroupDto;
import com.edson.teachercallroll.model.ModuleDto;
import com.edson.teachercallroll.model.SemestreDto;
import com.edson.teachercallroll.viewmodel.SelectGroupViewModel;
import com.edson.teachercallroll.viewmodel.StudentsInSheetViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Observer;

public class SelectGroupActivity extends AppCompatActivity {

    private TextView txtVFormation;
    private TextView txtVSemestre;
    private TextView txtVModule;
    private TextView txtVGroup;

    //Rough data
    private ArrayList<SemestreDto> semestreListTV;
    private ArrayList<ModuleDto> modulesListTV;
    private ArrayList<GroupDto> groupListTV;

    //Data switchable
    private ArrayList<ModuleDto> moduleDtos;
    private ArrayList<SemestreDto> semestreDtos;
    private ArrayList<GroupDto> groupDtos;
    private ArrayList<FormationDto> formationDtos;

    private Dialog dialog;
    private SelectGroupViewModel viewModel;

    //Adapters
    private FormationAdapter formationAdapter = null;
    private SemestreAdapter semestreAdapter = null;
    private GroupAdapter groupAdapter = null;
    private ModuleAdapter moduleAdapter = null;

    //id's to create a new AssistanceSheet
    private long idSemestre = 0;
    private long idModule = 0;
    private long idGroup = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_group);
        viewModel = new ViewModelProvider(SelectGroupActivity.this).get(SelectGroupViewModel.class);
        setFormationLists();
        setupComponents();
    }

    public void startQRCodeActivity(View view) {
        try {
            SharedPreferences shPref = getApplicationContext().getSharedPreferences("TeacherCallRoll_ShPref", 0);
            String token = shPref.getString("auth_token", null);
            if (token != null && idSemestre != 0 && idModule != 0 && idGroup != 0) {
                Log.i("startQRCodeActivity", "idSemestre: " + idSemestre);
                viewModel.addAsistanceSheet(token, idSemestre, idModule, idGroup).observe(this, (Observer<String>) id -> {
                    Toast.makeText(view.getContext(), "Feuille Généré", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, GenerateQrCodeActivity.class);
                    intent.putExtra("idSheet", id);
                    intent.putExtra("dateSheet", viewModel.getDate());
                    startActivity(intent);

                });
            } else {
                Toast.makeText(view.getContext(), "Veuillez selectionner un groupe !", Toast.LENGTH_LONG).show();
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    public void setupComponents() {
        txtVFormation = findViewById(R.id.txtVFormation);
        txtVSemestre = findViewById(R.id.txtVSemestre);
        txtVModule = findViewById(R.id.txtVModule);
        txtVGroup = findViewById(R.id.txtVGroup);
    }

    public void setupSpinners(View view) {
        //initialize dialog
        dialog = new Dialog(SelectGroupActivity.this);
        //set custom dialog
        dialog.setContentView(R.layout.dialog_searchable_spinner);
        //set custom height and hight
        dialog.getWindow().setLayout(1000, 1200);
        //set transparent background
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //Show dialog
        dialog.show();

        //initialize and asing variable
        EditText edtTxtSearch = dialog.findViewById(R.id.edtTxtSearch);
        ListView list_dialog = dialog.findViewById(R.id.list_dialog);
        TextView txtVSeleccioner = dialog.findViewById(R.id.txtVSeleccioner);

        //initialize array adapterholder
        try {
            switch (view.getId()) {
                case R.id.txtVFormation:
                    formationAdapter = new FormationAdapter(SelectGroupActivity.this, formationDtos);
                    list_dialog.setAdapter(formationAdapter);
                    txtVSeleccioner.setText("Sélectionner Formation");
                    edtTxtSearch.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            //filter array
                            formationAdapter.getFilter().filter(s);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    list_dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //when item selected from list
                            //set selected item on text view
                            txtVFormation.setText("Formation " + formationAdapter.getItem(position).getTitre_officiel());
                            //set the others list accord to the formation selected
                            semestreListTV = formationAdapter.getItem(position).getSemestreDtoList();
                            //set childs text views empty
                            txtVSemestre.setText("");
                            txtVModule.setText("");
                            txtVGroup.setText("");
                            //dismiss dialog
                            dialog.dismiss();
                        }
                    });
                    break;
                case R.id.txtVSemestre:
                    semestreAdapter = new SemestreAdapter(SelectGroupActivity.this, semestreListTV);
                    list_dialog.setAdapter(semestreAdapter);
                    txtVSeleccioner.setText("Sélectionner Semestre");
                    edtTxtSearch.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            //filter array
                            semestreAdapter.getFilter().filter(s);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    list_dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //when item selected from list
                            //set selected item on text view
                            txtVSemestre.setText("Semestre " + semestreAdapter.getItem(position).getSemestre());
                            //set the semestre_id to create AssistanceSheet
                            idSemestre = semestreAdapter.getItem(position).getId();
                            //set the others list accord to the formation selected
                            modulesListTV = semestreAdapter.getItem(position).getModuleDtoList();
                            groupListTV = semestreAdapter.getItem(position).getGroupDtoList();
                            //set childs text views empty
                            txtVModule.setText("");
                            txtVGroup.setText("");
                            //dismiss dialog
                            dialog.dismiss();
                        }
                    });
                    break;
                case R.id.txtVModule:
                    moduleAdapter = new ModuleAdapter(SelectGroupActivity.this, modulesListTV);
                    list_dialog.setAdapter(moduleAdapter);
                    txtVSeleccioner.setText("Sélectionner Module");
                    edtTxtSearch.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            //filter array
                            moduleAdapter.getFilter().filter(s);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    list_dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //when item selected from list
                            //set selected item on text view
                            txtVModule.setText("Module " + moduleAdapter.getItem(position).getTitre());
                            //set the semestre_id to create AssistanceSheet
                            idModule = moduleAdapter.getItem(position).getId();
                            //set childs text views empty
                            txtVGroup.setText("");
                            //dismiss dialog
                            dialog.dismiss();
                        }
                    });
                    break;
                case R.id.txtVGroup:
                    groupAdapter = new GroupAdapter(SelectGroupActivity.this, groupListTV);
                    list_dialog.setAdapter(groupAdapter);
                    txtVSeleccioner.setText("Sélectionner Groupe");
                    edtTxtSearch.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            //filter array
                            groupAdapter.getFilter().filter(s);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    list_dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //when item selected from list
                            //set selected item on text view
                            txtVGroup.setText("Groupe " + groupAdapter.getItem(position).getName());
                            //set the semestre_id to create AssistanceSheet
                            idGroup = groupAdapter.getItem(position).getId();
                            //dismiss dialog
                            dialog.dismiss();
                        }
                    });
                    break;
            }
        } catch (NullPointerException npe) {

        }
    }


    public void setFormationLists() {
        formationDtos = new ArrayList<>();
        //DUT semestre 1
        semestreDtos = new ArrayList<>();
        //groups
        groupDtos = new ArrayList<>();
        groupDtos.add(new GroupDto("S1", 1));
        groupDtos.add(new GroupDto("S1A", 2));
        groupDtos.add(new GroupDto("S1B", 3));
        groupDtos.add(new GroupDto("S1A1", 4));
        groupDtos.add(new GroupDto("S1A2", 5));
        groupDtos.add(new GroupDto("S1B1", 6));
        groupDtos.add(new GroupDto("S1B2", 7));
        //modules
        moduleDtos = new ArrayList<>();
        moduleDtos.add(new ModuleDto(1, "Introduction aux systèmes informatiques"));
        moduleDtos.add(new ModuleDto(2, "Introduction à l'algorithmique et à la programmation"));
        moduleDtos.add(new ModuleDto(3, "Structures de données et algorithmes fondamentaux"));
        moduleDtos.add(new ModuleDto(4, "Introduction aux bases de données"));
        moduleDtos.add(new ModuleDto(5, "Conception de documents et d'interfaces num."));
        moduleDtos.add(new ModuleDto(6, "Projet tutoré - Découverte"));
        moduleDtos.add(new ModuleDto(7, "Mathématiques discrètes"));
        moduleDtos.add(new ModuleDto(8, "Algèbre linéaire"));
        moduleDtos.add(new ModuleDto(9, "Environnement économique"));
        moduleDtos.add(new ModuleDto(10, "Fonctionnement des organisations"));
        moduleDtos.add(new ModuleDto(11, "Expr.-Com. - Fondamentaux de la com."));
        moduleDtos.add(new ModuleDto(12, "Anglais et informatique"));
        moduleDtos.add(new ModuleDto(13, "PPP - Connaître le monde professionnel"));
        moduleDtos.add(new ModuleDto(14, "Sport"));
        //semestreDtos
        semestreDtos.add(new SemestreDto(1, "1", groupDtos, moduleDtos));

        //DUT semestre 2
        //groups
        groupDtos = new ArrayList<>();
        groupDtos.add(new GroupDto("S1", 8));
        groupDtos.add(new GroupDto("S1A", 9));
        groupDtos.add(new GroupDto("S1B", 10));
        groupDtos.add(new GroupDto("S1A1", 11));
        groupDtos.add(new GroupDto("S1A2", 12));
        groupDtos.add(new GroupDto("S1B1", 13));
        groupDtos.add(new GroupDto("S1B2", 14));
        //modules
        moduleDtos = new ArrayList<>();
        moduleDtos.add(new ModuleDto(15, "Architecture et programmation des mécanismes de base d'un système informatique"));
        moduleDtos.add(new ModuleDto(16, "Architecture des réseaux"));
        moduleDtos.add(new ModuleDto(17, "Bases de la programmation orientée objet"));
        moduleDtos.add(new ModuleDto(18, "Bases de la conception orientée objet"));
        moduleDtos.add(new ModuleDto(19, "Introduction aux IHM"));
        moduleDtos.add(new ModuleDto(20, "Programmation et administration des bases de données"));
        moduleDtos.add(new ModuleDto(21, "Projet tutoré - Description et planification de projet"));
        moduleDtos.add(new ModuleDto(22, "Graphes et langages"));
        moduleDtos.add(new ModuleDto(23, "Analyse et méthodes numériques"));
        moduleDtos.add(new ModuleDto(24, "Environnement comptable, financier, juridique et social"));
        moduleDtos.add(new ModuleDto(25, "Gestion de projet informatique"));
        moduleDtos.add(new ModuleDto(26, "Expr.-Com. - Communication, information et argumentation"));
        moduleDtos.add(new ModuleDto(27, "Communiquer en anglais"));
        moduleDtos.add(new ModuleDto(28, "PPP - Identifier ses compétences"));
        moduleDtos.add(new ModuleDto(29, "Sport"));
        //semestre
        semestreDtos.add(new SemestreDto(2, "2", groupDtos, moduleDtos));

        //DUT semestre 3
        //groups
        groupDtos = new ArrayList<>();
        groupDtos.add(new GroupDto("S1", 15));
        groupDtos.add(new GroupDto("S1A", 16));
        groupDtos.add(new GroupDto("S1B", 17));
        groupDtos.add(new GroupDto("S1A1", 18));
        groupDtos.add(new GroupDto("S1A2", 19));
        groupDtos.add(new GroupDto("S1B1", 20));
        groupDtos.add(new GroupDto("S1B2", 21));
        //modules
        moduleDtos = new ArrayList<>();
        moduleDtos.add(new ModuleDto(30, "Principes des systèmes d'exploitation"));
        moduleDtos.add(new ModuleDto(31, "Services réseaux"));
        moduleDtos.add(new ModuleDto(32, "Algorithmique avancée"));
        moduleDtos.add(new ModuleDto(33, "Programmation Web - côté serveur"));
        moduleDtos.add(new ModuleDto(34, "Conception et programmation objet avancées"));
        moduleDtos.add(new ModuleDto(35, "Bases de données avancées"));
        moduleDtos.add(new ModuleDto(36, "Probabilités et statistiques"));
        moduleDtos.add(new ModuleDto(37, "Modélisations mathématiques"));
        moduleDtos.add(new ModuleDto(38, "Droit des technologies de l'information et de la communication (TIC)"));
        moduleDtos.add(new ModuleDto(39, "Gestion des systèmes d'information"));
        moduleDtos.add(new ModuleDto(40, "Expr.-Com. - Communication professionnelle"));
        moduleDtos.add(new ModuleDto(41, "Collaborer en anglais"));
        moduleDtos.add(new ModuleDto(42, "Méthodologie de la production d'applications"));
        moduleDtos.add(new ModuleDto(43, "Projet tutoré - Mise en situation professionnelle"));
        moduleDtos.add(new ModuleDto(44, "PPP - Préciser son projet"));
        moduleDtos.add(new ModuleDto(45, "Sport"));
        semestreDtos.add(new SemestreDto(3, "3", groupDtos, moduleDtos));

        //DUT semestre 4
        //groups
        groupDtos = new ArrayList<>();
        groupDtos.add(new GroupDto("S1", 22));
        groupDtos.add(new GroupDto("S1A", 23));
        groupDtos.add(new GroupDto("S1B", 24));
        groupDtos.add(new GroupDto("S1A1", 25));
        groupDtos.add(new GroupDto("S1A2", 26));
        groupDtos.add(new GroupDto("S1B1", 27));
        groupDtos.add(new GroupDto("S1B2", 28));
        //modules
        moduleDtos = new ArrayList<>();
        moduleDtos.add(new ModuleDto(46, "Administration système et réseau"));
        moduleDtos.add(new ModuleDto(47, "Traitement de l'information"));
        moduleDtos.add(new ModuleDto(48, "Développement iOS"));
        moduleDtos.add(new ModuleDto(49, "Programmation répartie"));
        moduleDtos.add(new ModuleDto(50, "Programmation Web - client riche"));
//        moduleDtos.add(new ModuleDto(51, "Programmation Web - client riche"));//DUT Repeat
        moduleDtos.add(new ModuleDto(52, "Conception et développement d'appli. mobiles"));
//        moduleDtos.add(new ModuleDto(53, "Conception et développement d'appli. mobiles"));//DUT Repeat
        moduleDtos.add(new ModuleDto(54, "Compléments d'informatique"));
        moduleDtos.add(new ModuleDto(55, "Compléments d'algorithmique"));
        moduleDtos.add(new ModuleDto(56, "Projet tutoré - Compléments"));
        moduleDtos.add(new ModuleDto(57, "Ateliers de création d'entreprise"));
//        moduleDtos.add(new ModuleDto(58, "Ateliers de création d'entreprise"));//DUT Repeat
        moduleDtos.add(new ModuleDto(59, "Recherche opérationnelle et aide à la décision"));
        moduleDtos.add(new ModuleDto(60, "Recherche opérationnelle et compléments d'algèbre"));
        moduleDtos.add(new ModuleDto(61, "Expr.-Com. - Communication dans les organisations"));
        moduleDtos.add(new ModuleDto(62, "Travailler en anglais"));
        moduleDtos.add(new ModuleDto(63, "Stage professionnel"));
        //semestre
        semestreDtos.add(new SemestreDto(4, "4", groupDtos, moduleDtos));
        //formation
        formationDtos.add(new FormationDto("FORM14923", "DUT Informatique", semestreDtos));


        //LP semestre 1
        semestreDtos = new ArrayList<>();
        //groups
        groupDtos = new ArrayList<>();
        groupDtos.add(new GroupDto("S1", 43));
        groupDtos.add(new GroupDto("S1A", 44));
        groupDtos.add(new GroupDto("S1B", 45));
        groupDtos.add(new GroupDto("S1A1", 46));
        groupDtos.add(new GroupDto("S1A2", 47));
        groupDtos.add(new GroupDto("S1B1", 48));
        groupDtos.add(new GroupDto("S1B2", 49));
        //modules
        moduleDtos = new ArrayList<>();
        moduleDtos.add(new ModuleDto(64, "Communication"));
        moduleDtos.add(new ModuleDto(65, "Anglais"));
        moduleDtos.add(new ModuleDto(66, "Gestion de projet"));
        moduleDtos.add(new ModuleDto(67, "Base de données : PHP/MySQL Big Data"));
        moduleDtos.add(new ModuleDto(68, "Remise à niveau R&amp;T"));
        moduleDtos.add(new ModuleDto(69, "Remise à niveau programmation objets mobiles"));
        moduleDtos.add(new ModuleDto(70, "Interfaces"));
        moduleDtos.add(new ModuleDto(71, "Sécurité"));
        moduleDtos.add(new ModuleDto(72, "Mobilité dév. mobile Android"));
        moduleDtos.add(new ModuleDto(73, "Développement iOS"));
        moduleDtos.add(new ModuleDto(74, "Technologies des réseaux sans fil"));
        moduleDtos.add(new ModuleDto(75, "Gestion des réseaux sans fil"));
        moduleDtos.add(new ModuleDto(76, "IPv6 pour mobile, 6lo WAN"));
        moduleDtos.add(new ModuleDto(77, "Dvpt sur OS classique"));
        moduleDtos.add(new ModuleDto(78, "Développement multiplateforme"));
        moduleDtos.add(new ModuleDto(79, "Applications innovantes"));
        moduleDtos.add(new ModuleDto(80, "Mobile Cloud"));
        moduleDtos.add(new ModuleDto(81, "Système de transport intelligent (ITS)"));
        moduleDtos.add(new ModuleDto(82, "Composants pour l'internet des objets"));
        moduleDtos.add(new ModuleDto(83, "Sécurité avancée des réseaux sans fil"));
        moduleDtos.add(new ModuleDto(84, "Capteurs et composants"));
        moduleDtos.add(new ModuleDto(85, "Fonctions de localisation et réseaux associés"));
        moduleDtos.add(new ModuleDto(86, "Développement web client"));
        moduleDtos.add(new ModuleDto(87, "Développement web serveur"));
        moduleDtos.add(new ModuleDto(88, "Dvpt web avec frameworks"));
        moduleDtos.add(new ModuleDto(89, "Projet tutoré"));
        moduleDtos.add(new ModuleDto(90, "Stage"));
        /*LP Repeat
//        moduleDtos.add(new ModuleDto(91, "Communication"));
//        moduleDtos.add(new ModuleDto(92, "Communication"));
//        moduleDtos.add(new ModuleDto(93, "Communication"));
//        moduleDtos.add(new ModuleDto(94, "Anglais"));
//        moduleDtos.add(new ModuleDto(95, "Gestion de projet"));
//        moduleDtos.add(new ModuleDto(96, "Base de données : PHP/MySQL Big Data"));
//        moduleDtos.add(new ModuleDto(97, "Remise à niveau R&amp;T"));
//        moduleDtos.add(new ModuleDto(98, "Remise à niveau programmation objets mobiles"));
//        moduleDtos.add(new ModuleDto(99, "Interfaces"));
//        moduleDtos.add(new ModuleDto(100, "Sécurité"));
//        moduleDtos.add(new ModuleDto(101, "Mobilité dév. mobile Android"));
//        moduleDtos.add(new ModuleDto(102, "Développement iOS"));
//        moduleDtos.add(new ModuleDto(103, "Technologies des réseaux sans fil"));
//        moduleDtos.add(new ModuleDto(104, "Gestion des réseaux sans fil"));
//        moduleDtos.add(new ModuleDto(105, "IPv6 pour mobile, 6lo WAN"));
//        moduleDtos.add(new ModuleDto(106, "Dvpt sur OS classique"));
//        moduleDtos.add(new ModuleDto(107, "Développement multiplateforme"));
//        moduleDtos.add(new ModuleDto(108, "Applications innovantes"));
//        moduleDtos.add(new ModuleDto(109, "Mobile Cloud"));
//        moduleDtos.add(new ModuleDto(110, "Système de transport intelligent (ITS)"));
//        moduleDtos.add(new ModuleDto(111, "Composants pour l'internet des objets"));
//        moduleDtos.add(new ModuleDto(112, "Sécurité avancée des réseaux sans fil"));
//        moduleDtos.add(new ModuleDto(113, "Capteurs et composants"));
//        moduleDtos.add(new ModuleDto(114, "Fonctions de localisation et réseaux associés"));
//        moduleDtos.add(new ModuleDto(115, "Développement web client"));
//        moduleDtos.add(new ModuleDto(116, "Développement web serveur"));
//        moduleDtos.add(new ModuleDto(117, "Dvpt web avec frameworks"));
//        moduleDtos.add(new ModuleDto(118, "Projet tutoré"));
//        moduleDtos.add(new ModuleDto(119, "Stage"));
        */
        //semestre
        semestreDtos.add(new SemestreDto(7, "1", groupDtos, moduleDtos));
        //formation
        formationDtos.add(new FormationDto("FORM10035", "LP Métiers de l'informatique : applications web", semestreDtos));
    }

}