package com.kelly.registerform.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kelly.registerform.R;
import com.kelly.registerform.controllers.DeparmentController;
import com.kelly.registerform.controllers.ProvinceController;
import com.kelly.registerform.dataAccess.AsociacionDA;
import com.kelly.registerform.dataAccess.DepartmentDA;
import com.kelly.registerform.dataAccess.DistrictDA;
import com.kelly.registerform.dataAccess.EmpresaCertificadoraDA;
import com.kelly.registerform.dataAccess.EspecieCrianzaDA;
import com.kelly.registerform.dataAccess.EstadoCivilDA;
import com.kelly.registerform.dataAccess.GrupoAgricolaDA;
import com.kelly.registerform.dataAccess.GrupoProductoTransformadoDA;
import com.kelly.registerform.dataAccess.ManejoCrianzaDA;
import com.kelly.registerform.dataAccess.MedidaChacraDA;
import com.kelly.registerform.dataAccess.MedidaProduccionDA;
import com.kelly.registerform.dataAccess.OrganizacionRegionalDA;
import com.kelly.registerform.dataAccess.PresentacionDA;
import com.kelly.registerform.dataAccess.ProductoAgricolaDA;
import com.kelly.registerform.dataAccess.ProductoCrianzaDA;
import com.kelly.registerform.dataAccess.ProductoDerivadoDA;
import com.kelly.registerform.dataAccess.ProductoTransformadoDA;
import com.kelly.registerform.dataAccess.ProvinceDA;
import com.kelly.registerform.dataAccess.SistemaRiegoDA;
import com.kelly.registerform.dataAccess.TipoAlimentoDA;
import com.kelly.registerform.dataAccess.TipoCertificadoraDA;
import com.kelly.registerform.dataAccess.TipoPropiedadDA;
import com.kelly.registerform.dataAccess.TipoSocioDA;
import com.kelly.registerform.dataAccess.connectionDA.BigDataDA;
import com.kelly.registerform.dataAccess.practicas.AgricolaDA;
import com.kelly.registerform.dataAccess.practicas.AlimentacionDA;
import com.kelly.registerform.dataAccess.practicas.AmbientalDA;
import com.kelly.registerform.dataAccess.practicas.BiodiversidadDA;
import com.kelly.registerform.dataAccess.practicas.ManejoSueloDA;
import com.kelly.registerform.dataAccess.practicas.PlagaDA;
import com.kelly.registerform.dataAccess.practicas.SanitariaDA;
import com.kelly.registerform.model.Asociacion;
import com.kelly.registerform.model.Book;
import com.kelly.registerform.model.ManejoCrianza;
import com.kelly.registerform.model.MedidaProduccion;
import com.kelly.registerform.model.ProductoCrianza;
import com.kelly.registerform.model.ProductoTransformado;
import com.kelly.registerform.model.connection.BigData;
import com.kelly.registerform.model.main.MainJson;
import com.kelly.registerform.utils.SaveDataBase;
import com.kelly.registerform.utils.SharedPreferencesManager;
import com.kelly.registerform.view.commerce.ComercializacionActivity;
import com.kelly.registerform.view.farming.FarmingCertificationActivity;
import com.kelly.registerform.view.farming.ProductionActivity;
import com.kelly.registerform.view.farming.SystemProductionActivity;
import com.kelly.registerform.view.livestock.LivestockProductionActivity;
import com.kelly.registerform.view.partner.RegistrationPartnerPartBActivity;
import com.kelly.registerform.view.partner.ValidationActivity;
import com.kelly.registerform.view.transformation.ProcessActivity;
import com.orm.SugarContext;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    private Context context = this;
    private static int SPLASH_TIME_OUT = 7000;

    private Activity self;
    private int numPermissions = 0;
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        SugarContext.init(this);


        reviewDatabase();
        callDataBaseGetters();
        sharedPreferencesManager = SharedPreferencesManager.getInstance(context);
        if(sharedPreferencesManager.getState().equals("no")){
            SPLASH_TIME_OUT=7000;
            sharedPreferencesManager.saveState();
        }else{
            SPLASH_TIME_OUT=3000;
            System.out.println("less time");
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {

            runSplash();
        } else {
            runSplash();
        }
        MainJson mainJson = new MainJson(context);
    }


    private void callDataBaseGetters(){
        SaveDataBase saveDataBase = new SaveDataBase(context);

        saveDataBase.getDepartamentos();
        saveDataBase.getProvinces();
        saveDataBase.getDistricts();
        saveDataBase.getSocios();
        saveDataBase.getAsociacion();
        saveDataBase.getOrgRegional();

        saveDataBase.getEstadoCivil();
        saveDataBase.getTipoPropiedad();
        saveDataBase.getMedidaChacra();

        saveDataBase.getGrupoAgricola();
        saveDataBase.getProductoAgricola();
        saveDataBase.getSistemaRiego();

        saveDataBase.getManejoSuelo();
        saveDataBase.getBiodiversidad();
        saveDataBase.getPlaga();
        saveDataBase.getAgricola();

        saveDataBase.getTipoCertificadora();
        saveDataBase.getEmpresaCertificadora();

        saveDataBase.getEspecieCrianza();
        saveDataBase.getProductoEspecieCrianza();
        saveDataBase.getTipoAlimento();
        saveDataBase.getManejoCrianza();
        saveDataBase.getProductoDerivado();

        saveDataBase.getAmbiental();
        saveDataBase.getAlimentacion();
        saveDataBase.getSanitaria();

        saveDataBase.getGrupoProducto();
        saveDataBase.getProductoTransformado();
        saveDataBase.getPresentacion();

        saveDataBase.getMedidaProduccion();
    }
    private void runSplash() {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                // Start the next activity
                Intent mainIntent = new Intent(context, MainActivity.class);
                startActivity(mainIntent);

                // Close the activity so the user won't able to go back this
                // activity pressing Back button
                finish();
            }
        };
        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_TIME_OUT);
    }
    private void reviewDatabase(){
        DepartmentDA departmentDA = new DepartmentDA(context);
        ProvinceDA provinceDA = new ProvinceDA(context);
        DistrictDA districtDA = new DistrictDA(context);
        TipoSocioDA tipoSocioDA = new TipoSocioDA(context);
        AsociacionDA asociacionDA = new AsociacionDA(context);
        OrganizacionRegionalDA organizacionRegionalDA = new OrganizacionRegionalDA(context);
        EstadoCivilDA estadoCivilDA = new EstadoCivilDA(context);
        TipoPropiedadDA tipoPropiedadDA = new TipoPropiedadDA(context);
        MedidaChacraDA medidaChacraDA = new MedidaChacraDA(context);

        GrupoAgricolaDA grupoAgricolaDA = new GrupoAgricolaDA(context);
        ProductoAgricolaDA productoAgricolaDA = new ProductoAgricolaDA(context);
        SistemaRiegoDA sistemaRiegoDA = new SistemaRiegoDA(context);

        ManejoSueloDA manejoSueloDA = new ManejoSueloDA(context);
        PlagaDA PlagaDA = new PlagaDA(context);
        AgricolaDA agricolaDA = new AgricolaDA(context);
        BiodiversidadDA biodiversidadDA = new BiodiversidadDA(context);

        TipoCertificadoraDA tipoCertificadoraDA = new TipoCertificadoraDA(context);
        EmpresaCertificadoraDA emp = new EmpresaCertificadoraDA(context);

        EspecieCrianzaDA especieCrianzaDA = new EspecieCrianzaDA(context);
        ProductoCrianzaDA productoCrianzaDa = new ProductoCrianzaDA(context);
        TipoAlimentoDA tipoAlimentoDA = new TipoAlimentoDA(context);
        ManejoCrianzaDA manejoCrianzaDA = new ManejoCrianzaDA(context);
        ProductoDerivadoDA productoDerivadoDA = new ProductoDerivadoDA(context);

        AmbientalDA ambientalDA = new AmbientalDA(context);
        AlimentacionDA alimentacionDA=new AlimentacionDA(context);
        SanitariaDA sanitariaDA = new SanitariaDA(context);

        GrupoProductoTransformadoDA grupo=new GrupoProductoTransformadoDA(context);
        ProductoTransformadoDA productoTransformadoDA = new ProductoTransformadoDA(context);
        PresentacionDA presentacionDA  = new PresentacionDA(context);
        BigDataDA bigDataDA  = new BigDataDA(context);

        MedidaProduccionDA medidaProduccion  = new MedidaProduccionDA(context);
    }
}
