package com.kelly.registerform.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kelly.registerform.BuildConfig;
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
import com.kelly.registerform.dataAccess.practicas.AgricolaDA;
import com.kelly.registerform.dataAccess.practicas.AlimentacionDA;
import com.kelly.registerform.dataAccess.practicas.AmbientalDA;
import com.kelly.registerform.dataAccess.practicas.BiodiversidadDA;
import com.kelly.registerform.dataAccess.practicas.ManejoSueloDA;
import com.kelly.registerform.dataAccess.practicas.PlagaDA;
import com.kelly.registerform.dataAccess.practicas.SanitariaDA;
import com.kelly.registerform.model.Asociacion;
import com.kelly.registerform.model.EspecieCrianza;
import com.kelly.registerform.model.EstadoCivil;
import com.kelly.registerform.model.GrupoAgricola;
import com.kelly.registerform.model.GrupoProductoTransformado;
import com.kelly.registerform.model.ManejoCrianza;
import com.kelly.registerform.model.MedidaChacra;
import com.kelly.registerform.model.MedidaProduccion;
import com.kelly.registerform.model.OrganizacionRegional;
import com.kelly.registerform.model.Presentacion;
import com.kelly.registerform.model.ProductoAgricola;
import com.kelly.registerform.model.ProductoCrianza;
import com.kelly.registerform.model.ProductoDerivado;
import com.kelly.registerform.model.ProductoTransformado;
import com.kelly.registerform.model.SistemaRiego;
import com.kelly.registerform.model.TipoAlimento;
import com.kelly.registerform.model.TipoPropiedad;
import com.kelly.registerform.model.TipoSocio;
import com.kelly.registerform.model.certifications.EmpresaCertificadora;
import com.kelly.registerform.model.certifications.TipoCertificadora;
import com.kelly.registerform.model.practicas.Agricola;
import com.kelly.registerform.model.practicas.Alimentacion;
import com.kelly.registerform.model.practicas.Ambiental;
import com.kelly.registerform.model.practicas.Biodiversidad;
import com.kelly.registerform.model.practicas.ManejoSuelo;
import com.kelly.registerform.model.practicas.Plaga;
import com.kelly.registerform.model.practicas.Sanitaria;
import com.kelly.registerform.model.ubigeo.Departamento;
import com.kelly.registerform.model.ubigeo.Distrito;
import com.kelly.registerform.model.ubigeo.Provincia;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.kelly.registerform.dataAccess.DepartmentDA.stateDeparment;
import static com.kelly.registerform.dataAccess.ProvinceDA.stateProvincia;
/**
 * Created by Italo on 13/02/2018.
 */

public class SaveDataBase {
    private Context context;
    public  static RequestQueue queue;

    private static final String BASE_URL = BuildConfig.BASE_URL;
    public SaveDataBase(Context context){
        this.context=context;
        queue = Volley.newRequestQueue(this.context);
    }

    public void getDepartamentos(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://www.demodataexe.com/anpe/webservice/lista_ubigeo.php?tipo=DEPT&token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("ubigeos");
                            Iterator<?> keys = jsonObj2.keys();
                            Departamento d = new Departamento("0", "Elije");
                            if (!verifyDepartamento("0"))d.save();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String id = jsonDepartment.get("id").toString();
                                    String name = (String)jsonDepartment.get("ubigeo");
                                    Departamento departamento = new Departamento(id, name);
                                    if (!verifyDepartamento(id)) {
                                        departamento.save();
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);
    }

    public void getProvinces(){
        String url = "http://www.demodataexe.com/anpe/webservice/lista_ubigeo.php?tipo=PROV&token=lpsk.21568%24lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("ubigeos");
                            Iterator<?> keys = jsonObj2.keys();

                            Provincia p = new Provincia("0","0","Elije");
                            if (!verifyProvincia("0"))p.save();

                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  id = jsonDepartment.get("id")+"";
                                    String name =jsonDepartment.get("ubigeo")+"";
                                    String idParent =jsonDepartment.get("pertenece")+"";
                                    Provincia provincia = new Provincia(id,idParent,name);
                                    if(!verifyProvincia(id)){

                                        provincia.save();
                                    }

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);
    }

    public void getDistricts(){
        String url = "http://www.demodataexe.com/anpe/webservice/lista_ubigeo.php?tipo=DIST&token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("ubigeos");
                            Iterator<?> keys = jsonObj2.keys();

                            Distrito d = new Distrito("0","0","Elije");
                            if(!verifyDistrict("0"))d.save();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  id = jsonDepartment.get("id")+"";
                                    String name =jsonDepartment.get("ubigeo")+"";
                                    String idParent =jsonDepartment.get("pertenece")+"";
                                    Distrito distrito = new Distrito(id,idParent,name);
                                    if(!verifyDistrict(id)){

                                        distrito.save();
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);

    }

    public void getSocios(){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = BuildConfig.BASE_URL+"lista_tipo_socio.php?token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("tipos_socio");
                            Iterator<?> keys = jsonObj2.keys();
                            TipoSocio s = new TipoSocio("0","Elije");
                            if(!verifyTipoSocio("0"))s.save();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                TipoSocio tipoSocio;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  id = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("tipo_socio");
                                    tipoSocio = new TipoSocio(id,name);
                                    if(!verifyTipoSocio(id))tipoSocio.save();
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    tipoSocio = new TipoSocio(key,name);
                                    if(!verifyTipoSocio(key))tipoSocio.save();

                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);

    }

    public void getAsociacion(){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = BuildConfig.BASE_URL+"lista_tipo_asociacion.php?token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("asociaciones");
                            Iterator<?> keys = jsonObj2.keys();
                            Asociacion a = new Asociacion("0","Elije");
                            if(!verifyAsociacion("0"))a.save();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                Asociacion asociacion;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  id = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("asociacion");
                                    asociacion = new Asociacion(id,name);
                                    if(!verifyAsociacion(id))asociacion.save();
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    asociacion = new Asociacion(key,name);
                                    if(!verifyAsociacion(key))asociacion.save();
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);

    }

    public void getOrgRegional(){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = BuildConfig.BASE_URL+"lista_tipo_organizacion_regional.php?token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("organizaciones_regionales");
                            Iterator<?> keys = jsonObj2.keys();
                            OrganizacionRegional o = new OrganizacionRegional("0","Elije");
                            o.save();
                            if(!verifyOrgRegional("0"))o.save();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                OrganizacionRegional organizacionRegional;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  id = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("organizacion");
                                    organizacionRegional = new OrganizacionRegional(id,name);
                                    if(!verifyOrgRegional(id))organizacionRegional.save();
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    organizacionRegional = new OrganizacionRegional(key,name);
                                    if(!verifyOrgRegional(key))organizacionRegional.save();
                                }
                            }


                        } catch (JSONException e) {
                            //s_regional.setAdapter(null);
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);

    }

    public void getEstadoCivil(){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = BuildConfig.BASE_URL+"lista_estado_civil.php?token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("estados_civiles");
                            Iterator<?> keys = jsonObj2.keys();
                            EstadoCivil e = new EstadoCivil("0","Elije");
                            if(!verifyEstadoCivil("0"))e.save();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                System.out.println(key);
                                EstadoCivil estadoCivil;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  id = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("estado_civil");
                                    estadoCivil = new EstadoCivil(id,name);
                                    if(!verifyEstadoCivil(id))estadoCivil.save();
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    estadoCivil = new EstadoCivil(key,name);
                                    if(!verifyEstadoCivil(key))estadoCivil.save();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);


    }

    public void getTipoPropiedad(){

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BuildConfig.BASE_URL+"lista_tipo_propiedad.php?token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("tipos_propiedad");
                            Iterator<?> keys = jsonObj2.keys();
                            TipoPropiedad t = new TipoPropiedad("0","Elije");
                            if(!verifyTipoPropiedad("0"))t.save();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                TipoPropiedad tipoPropiedad;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  id = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("tipo_propiedad");
                                    tipoPropiedad = new TipoPropiedad(id,name);
                                    if(!verifyTipoPropiedad(id))tipoPropiedad.save();
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    tipoPropiedad = new TipoPropiedad(key,name);
                                    if(!verifyTipoPropiedad(key))tipoPropiedad.save();
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);


    }

    public void getMedidaChacra(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BuildConfig.BASE_URL+"lista_tipo_medida.php?token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("tipos_medidas");
                            Iterator<?> keys = jsonObj2.keys();
                            MedidaChacra m = new MedidaChacra("0","Elije");
                            if(!verifyMedidaChacra("0"))m.save();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                MedidaChacra medidaChacra ;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  id = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("tipo_medida");
                                    medidaChacra = new MedidaChacra(id,name);
                                    if(!verifyMedidaChacra(id))medidaChacra.save();
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    medidaChacra = new MedidaChacra(key,name);
                                    if(!verifyMedidaChacra(key))medidaChacra.save();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);


    }

    public void getGrupoAgricola(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BuildConfig.BASE_URL+"lista_grupos_productos_agricolas.php?&token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("grupos_productos_agricolas");
                            Iterator<?> keys = jsonObj2.keys();
                            GrupoAgricola g = new GrupoAgricola("0","Elije");
                            if(!verifyGrupoAgricola("0"))g.save();

                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                GrupoAgricola grupoAgricola ;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  id = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("grupo");
                                    grupoAgricola=new GrupoAgricola(id,name);
                                  if(!verifyGrupoAgricola(id))grupoAgricola.save();
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    grupoAgricola=new GrupoAgricola(key,name);
                                    if(!verifyGrupoAgricola(key))grupoAgricola.save();
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);
    }

    public void getProductoAgricola(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://www.demodataexe.com/anpe/webservice/lista_productos_agricolas.php?&token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("productos_agricolas");
                            Iterator<?> keys = jsonObj2.keys();
                            ProductoAgricola p = new ProductoAgricola("0","0","Elije","vacio");
                            if(!verifyGrupoAgricola("0"))p.save();

                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                ProductoAgricola productoAgricola ;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  id = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("producto");
                                    String id_grupo =(String)jsonDepartment.get("id_grupo");
                                    String tipo_cultivo =(String)jsonDepartment.get("tipo_cultivo");
                                    productoAgricola=new ProductoAgricola(id,id_grupo,name,tipo_cultivo);
                                    if(!verifyProductoAgricola(id))productoAgricola.save();
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    //productoAgricola=new GrupoAgricola(key,name);
                                    //if(!verifyGrupoAgricola(key))productoAgricola.save();
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);

    }

    public void getSistemaRiego(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url_riego = BuildConfig.BASE_URL+"lista_sistemas_riego.php?&token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequestRiego = new StringRequest(Request.Method.GET, url_riego,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("sistemas_riego");
                            Iterator<?> keys = jsonObj2.keys();
                            SistemaRiego s = new SistemaRiego("0","Elije");
                            if(!verifySistemaRiego("0"))s.save();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                SistemaRiego sistemaRiego;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String id = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("sistema");
                                    sistemaRiego = new SistemaRiego(id,name);
                                    if(!verifySistemaRiego(id))sistemaRiego.save();
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    sistemaRiego = new SistemaRiego(key,name);
                                    if(!verifySistemaRiego(key))sistemaRiego.save();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequestRiego);

    }

    public void getManejoSuelo(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://www.demodataexe.com/anpe/webservice/lista_practicas_agroecologicas.php?tipo=manejo_suelos&token=lpsk.21568$lsjANPIO02";
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("practicas");
                            Iterator<?> keys = jsonObj2.keys();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                ManejoSuelo manejoSuelo;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  idDis = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("practica");
                                    manejoSuelo = new ManejoSuelo(idDis,name);
                                    if(!verifyManejoSuelo(idDis))manejoSuelo.save();
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    manejoSuelo = new ManejoSuelo(key,name);
                                    if(!verifyManejoSuelo(key))manejoSuelo.save();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);

    }

    public void getBiodiversidad(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://www.demodataexe.com/anpe/webservice/lista_practicas_agroecologicas.php?tipo=biodiversidad&token=lpsk.21568$lsjANPIO02";
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("practicas");
                            Iterator<?> keys = jsonObj2.keys();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                Biodiversidad biodiversidad;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  idDis = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("practica");
                                    biodiversidad = new Biodiversidad(idDis,name);
                                    if(!verifyBiodiversidad(idDis))biodiversidad.save();
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    biodiversidad = new Biodiversidad(key,name);
                                    if(!verifyBiodiversidad(key))biodiversidad.save();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);

    }

    public void getPlaga(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://www.demodataexe.com/anpe/webservice/lista_practicas_agroecologicas.php?tipo=plagas&token=lpsk.21568$lsjANPIO02";
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("practicas");
                            Iterator<?> keys = jsonObj2.keys();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                Plaga plaga;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  idDis = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("practica");
                                    plaga = new Plaga(idDis,name);
                                    if(!verifyPlaga(idDis))plaga.save();
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    plaga = new Plaga(key,name);
                                    if(!verifyPlaga(key))plaga.save();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);

    }

    public void getAgricola(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://www.demodataexe.com/anpe/webservice/lista_practicas_agroecologicas.php?tipo=agricolas&token=lpsk.21568$lsjANPIO02";
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("practicas");
                            Iterator<?> keys = jsonObj2.keys();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                Agricola agricola;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  idDis = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("practica");
                                    agricola = new Agricola(idDis,name);
                                    if(!verifyAgricola(idDis))agricola.save();
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    agricola = new Agricola(key,name);
                                    if(!verifyAgricola(key))agricola.save();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);

    }

    public void getTipoCertificadora(){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = BuildConfig.BASE_URL+"lista_empresas_certificadoras_sgp.php?token=lpsk.21568$lsjANPIO022";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("empresas_sgp");
                            Iterator<?> keys = jsonObj2.keys();
                            TipoCertificadora t = new TipoCertificadora("0","Elije");
                            if(!verifyTipoCertificadora("0"))t.save();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();

                                TipoCertificadora tipoCertificadora;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  idDis = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("empresa_sgp");
                                    tipoCertificadora = new TipoCertificadora(idDis,name);
                                    if(!verifyTipoCertificadora(idDis))tipoCertificadora.save();
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    tipoCertificadora = new TipoCertificadora(key,name);
                                    if(!verifyTipoCertificadora(key))tipoCertificadora.save();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);

    }

    public void getEmpresaCertificadora(){

        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = BuildConfig.BASE_URL+"lista_empresas_certificadoras.php?token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("empresas_certificadoras");
                            Iterator<?> keys = jsonObj2.keys();
                            EmpresaCertificadora e = new EmpresaCertificadora("0","Elije");
                            if(!verifyEmpresaCertificadora("0"))e.save();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();

                                EmpresaCertificadora empresaCertificadora;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  idDis = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("empresa");
                                    empresaCertificadora = new EmpresaCertificadora(idDis,name);
                                    if(!verifyEmpresaCertificadora(idDis))empresaCertificadora.save();
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    empresaCertificadora = new EmpresaCertificadora(key,name);
                                    if(!verifyEmpresaCertificadora(key))empresaCertificadora.save();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);

    }

    public void getEspecieCrianza(){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = BuildConfig.BASE_URL+"lista_grupos_animales_crianza.php?token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("grupo_animales_crianza");
                            Iterator<?> keys = jsonObj2.keys();
                            EspecieCrianza e = new EspecieCrianza("0","Elije");
                            if(!verifyEspecieCrianza("0"))e.save();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                EspecieCrianza especieCrianza;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  id = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("grupo");
                                    especieCrianza = new EspecieCrianza(id,name);
                                    if(!verifyEspecieCrianza(id))especieCrianza.save();
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    especieCrianza = new EspecieCrianza(key,name);
                                    if(!verifyEspecieCrianza(key))especieCrianza.save();
                                }
                            }

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);

    }

    public void getProductoEspecieCrianza(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://demodataexe.com/anpe/webservice/lista_animales_crianza.php?&token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("animales_crianza");
                            Iterator<?> keys = jsonObj2.keys();
                            ProductoCrianza p = new ProductoCrianza("0","0","Elije");
                            if(!verifyProductoEspecieCrianza("0"))p.save();

                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                ProductoCrianza productoCrianza ;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  id = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("animal");
                                    String id_grupo =(String)jsonDepartment.get("id_grupo");
                                    productoCrianza=new ProductoCrianza(id,id_grupo,name);
                                    if(!verifyProductoEspecieCrianza(id))productoCrianza.save();
                                }else{
                                    String name= (String)jsonObj2.get(key);
                                    //productoAgricola=new GrupoAgricola(key,name);
                                    //if(!verifyGrupoAgricola(key))productoAgricola.save();
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);
    }

    public void getTipoAlimento(){

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BuildConfig.BASE_URL+"lista_tipo_alimento.php?token=lpsk.21568$lsjANPIO02";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("tipos_alimento");
                            Iterator<?> keys = jsonObj2.keys();
                            TipoAlimento t = new TipoAlimento("0","Elije");
                            if(!verifyTipoAlimento("0"))t.save();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                TipoAlimento tipoAlimento;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  id = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("alimento");
                                    tipoAlimento = new TipoAlimento(id,name);
                                    if(!verifyTipoAlimento("0"))tipoAlimento.save();
                                }else{

                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);
    }

    public void getManejoCrianza(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BuildConfig.BASE_URL+"lista_tipo_manejo_crianza.php?token=lpsk.21568$lsjANPIO022";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("tipos_manejo_crianza");
                            Iterator<?> keys = jsonObj2.keys();
                            ManejoCrianza m = new ManejoCrianza("0","Elije");
                            if(!verifyManejoCrianza("0"))m.save();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                ManejoCrianza manejoCrianza;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String id = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("manejo_crianza");
                                    manejoCrianza = new ManejoCrianza(id,name);
                                    if(!verifyManejoCrianza(id))manejoCrianza.save();
                                }else{
                                    String name= (String)jsonObj2.get(key);

                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);
    }

    public void getProductoDerivado(){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = BuildConfig.BASE_URL+"lista_productos_derivados.php?token=lpsk.21568$lsjANPIO02";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("productos_derivados");
                            Iterator<?> keys = jsonObj2.keys();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                ProductoDerivado productoDerivado;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  idDis = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("producto_derivado");
                                    productoDerivado = new ProductoDerivado(idDis,name);
                                    if(!verifyProductoDerivado(idDis))productoDerivado.save();

                                }else{

                                    String name= (String)jsonObj2.get(key);
                                    productoDerivado = new ProductoDerivado(key,name);
                                    if(!verifyProductoDerivado(key))productoDerivado.save();

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);
    }

    public void getAmbiental(){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = BuildConfig.BASE_URL+"lista_practicas_agroecologicas.php?tipo=ambientales&token=lpsk.21568$lsjANPIO02";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("practicas");
                            Iterator<?> keys = jsonObj2.keys();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                Ambiental ambiental;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  idDis = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("practica");
                                    ambiental = new Ambiental(idDis,name);
                                    if(!verifyAmbiental(idDis))ambiental.save();
                                }else{

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);
    }

    public void getAlimentacion(){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = BuildConfig.BASE_URL+"lista_practicas_agroecologicas.php?tipo=alimentacion&token=lpsk.21568$lsjANPIO02";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("practicas");
                            Iterator<?> keys = jsonObj2.keys();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                Alimentacion alimentacion;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  idDis = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("practica");
                                    alimentacion = new Alimentacion(idDis,name);
                                    if(!verifyAlimentacion(idDis))alimentacion.save();
                                }else{

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);
    }

    public void getSanitaria(){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = BuildConfig.BASE_URL+"lista_practicas_agroecologicas.php?tipo=sanitario&token=lpsk.21568$lsjANPIO02";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("practicas");
                            Iterator<?> keys = jsonObj2.keys();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                Sanitaria sanitaria;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  idDis = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("practica");
                                    sanitaria = new Sanitaria(idDis,name);
                                    if(!verifySanitaria(idDis))sanitaria.save();
                                }else{

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);
    }

    public void getGrupoProducto(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BuildConfig.BASE_URL+"lista_grupos_productos_transformados.php?token=lpsk.21568$lsjANPIO02";
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("grupo_productos_transformados");
                            Iterator<?> keys = jsonObj2.keys();
                            GrupoProductoTransformado g = new GrupoProductoTransformado("0","Elije");
                            if(!verifyGrupoProducto("0"))g.save();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                GrupoProductoTransformado grupo;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  id = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("grupo");
                                    grupo = new GrupoProductoTransformado(id,name);
                                    if(!verifyGrupoProducto(id))grupo.save();
                                }else{

                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);
    }

    public void getProductoTransformado(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://demodataexe.com/anpe/webservice/lista_productos_transformados.php?&token=lpsk.21568$lsjANPIO02";
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("productos_transformados");
                            Iterator<?> keys = jsonObj2.keys();
                            ProductoTransformado p = new ProductoTransformado("0","0","Elije");
                            if(!verifyProductoTransformado("0"))p.save();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                ProductoTransformado productoTransformado;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  id = jsonDepartment.get("id").toString();
                                    String  id_grupo = jsonDepartment.get("id_grupo").toString();
                                    String name =(String)jsonDepartment.get("producto_transformado");
                                    productoTransformado = new ProductoTransformado(id,id_grupo,name);
                                    if(!verifyProductoTransformado(id))productoTransformado.save();
                                }else{
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);
    }

    public void getPresentacion(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BuildConfig.BASE_URL+"lista_tipo_presentacion.php?&token=lpsk.21568$lsjANPIO02";
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("tipos_presentacion");
                            Iterator<?> keys = jsonObj2.keys();
                            Presentacion p = new Presentacion("0","Elije");
                            if(!verifyPresentacion("0"))p.save();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                Presentacion presentacion;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  id = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("presentacion");
                                    presentacion = new Presentacion(id,name);
                                    if(!verifyPresentacion(id))presentacion.save();
                                }else{

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");

            }
        });
        queue.add(stringRequest);

    }

    public void getMedidaProduccion(){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://www.demodataexe.com/anpe/webservice/lista_tipo_medida_produccion.php?token=lpsk.21568$lsjANPIO02";
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONObject jsonObj2 = (JSONObject) jsonObj.get("tipos_medida_produccion");
                            Iterator<?> keys = jsonObj2.keys();
                            MedidaProduccion m = new MedidaProduccion("0","Elije");
                            if(!verifyPresentacion("0"))m.save();
                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                MedidaProduccion medidaProduccion;
                                if ( jsonObj2.get(key) instanceof JSONObject ) {
                                    JSONObject jsonDepartment = (JSONObject) jsonObj2.get(key);
                                    String  id = jsonDepartment.get("id").toString();
                                    String name =(String)jsonDepartment.get("medida");
                                    medidaProduccion = new MedidaProduccion(id,name);
                                    if(!verifyMedidaProduccion(id))medidaProduccion.save();
                                }else{

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Fail","That didn't work!");
            }
        });
        queue.add(stringRequest);

    }
    public boolean verifyDepartamento(String id){
        List<Departamento>departamentoList = DepartmentDA.departamentoList;
        if(departamentoList==null)return false;
        if(departamentoList.size()==0)return false;
        for (int i=0;i<departamentoList.size();i++){
            if(id.equals(departamentoList.get(i).getId_departamento()))return true;
        }
        return false;
    }

    public boolean verifyProvincia(String id){
        List<Provincia>provinciaList = ProvinceDA.provinciaList;
        if(provinciaList==null)return false;
        if(provinciaList.size()==0)return false;
        for (int i=0;i<provinciaList.size();i++){
            if(id.equals(provinciaList.get(i).getId_provincia()))return true;
        }
        return false;
    }

    public boolean verifyDistrict(String id){
        List<Distrito>distritoList = DistrictDA.distritoList;
        if(distritoList==null)return false;
        if(distritoList.size()==0)return false;
        for (int i=0;i<distritoList.size();i++){
            if(id.equals(distritoList.get(i).getId_ditrito()))return true;
        }
        return false;
    }

    public boolean verifyTipoSocio(String id){
        List<TipoSocio>list = TipoSocioDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifyAsociacion(String id){
        List<Asociacion>list = AsociacionDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifyOrgRegional(String id){
        List<OrganizacionRegional>list = OrganizacionRegionalDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifyEstadoCivil(String id){
        List<EstadoCivil>list = EstadoCivilDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifyMedidaChacra(String id){
        List<MedidaChacra>list = MedidaChacraDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifyTipoPropiedad(String id){
        List<TipoPropiedad>list = TipoPropiedadDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifyGrupoAgricola(String id){
        List<GrupoAgricola>list = GrupoAgricolaDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifyProductoAgricola(String id){
        List<ProductoAgricola>list = ProductoAgricolaDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifySistemaRiego(String id){
        List<SistemaRiego>list = SistemaRiegoDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifyManejoSuelo(String id){
        List<ManejoSuelo>list = ManejoSueloDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifyBiodiversidad(String id){
        List<Biodiversidad>list = BiodiversidadDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifyPlaga(String id){
        List<Plaga>list = PlagaDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifyAgricola(String id){
        List<Agricola>list = AgricolaDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifyTipoCertificadora(String id){
        List<TipoCertificadora>list = TipoCertificadoraDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifyEmpresaCertificadora(String id){
        List<EmpresaCertificadora>list = EmpresaCertificadoraDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifyEspecieCrianza(String id){
        List<EspecieCrianza>list = EspecieCrianzaDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifyProductoEspecieCrianza(String id){
        List<ProductoCrianza>list = ProductoCrianzaDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifyTipoAlimento(String id){
        List<TipoAlimento>list = TipoAlimentoDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifyManejoCrianza(String id){
        List<ManejoCrianza>list = ManejoCrianzaDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifyProductoDerivado(String id){
        List<ProductoDerivado>list = ProductoDerivadoDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifyAmbiental(String id){
        List<Ambiental>list = AmbientalDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifyAlimentacion(String id){
        List<Alimentacion>list = AlimentacionDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifySanitaria(String id){
        List<Sanitaria>list = SanitariaDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifyGrupoProducto(String id){
        List<GrupoProductoTransformado>list = GrupoProductoTransformadoDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifyProductoTransformado(String id){
        List<ProductoTransformado>list = ProductoTransformadoDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifyPresentacion(String id){
        List<Presentacion>list = PresentacionDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }

    public boolean verifyMedidaProduccion(String id){
        List<Presentacion>list = PresentacionDA.list;
        if(list==null)return false;
        if(list.size()==0)return false;
        for (int i=0;i<list.size();i++){
            if(id.equals(list.get(i).getId_main()))return true;
        }
        return false;
    }
}