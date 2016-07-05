package com.MinimalSoft.BrujulaUniversitaria;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    android.support.v7.app.ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        setToolbar();

        if (getSupportActionBar() != null) // Habilitar up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /**
         * Intent i = getIntent();
         String name = i.getStringExtra(EXTRA_NAME);
         int idDrawable = i.getIntExtra(EXTRA_DRAWABLE, -1);

         CollapsingToolbarLayout collapser =
         (CollapsingToolbarLayout) findViewById(R.id.collapser);
         collapser.setTitle(name); // Cambiar título

         loadImageParallax(idDrawable);// Cargar Imagen

         actionBar = getSupportActionBar();
         actionBar.setDisplayShowHomeEnabled(true);
         */

        setUber();

        Bundle b = getIntent().getExtras();
        String value = b.getString("Titulo");
        
        setActivity(value);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_share:
                showSnackBar("Share");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSnackBar(String msg) {
        Snackbar
                .make(findViewById(R.id.coordinator), msg, Snackbar.LENGTH_LONG)
                .show();
    }

    private void setToolbar() {
        // Añadir la Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    /**
     * Se carga una imagen aleatoria para el detalle

    private void loadImageParallax(int id) {
        ImageView image = (ImageView) findViewById(R.id.image_paralax);
        // Usando Glide para la carga asíncrona
        Glide.with(this)
                .load(id)
                .centerCrop()
                .into(image);
    }
     */

    private void setUber (){

        /**
        RequestButton requestButton = (RequestButton) findViewById(R.id.uberb);
        requestButton.setText("Pedir un Uber");
        RideParameters rideParams = new RideParameters.Builder()
                .setProductId("a1111c8c-c720-46c3-8534-2fcdd730040d")
                .setPickupLocation(19.305917f, -99.108155f, "Tu Ubicacion Actual", "\n")
                .setDropoffLocation(19.376468f, -99.178189f, "El califa", "\n")
                .build();
        requestButton.setRideParameters(rideParams);

         */

        FloatingActionButton Uber = (FloatingActionButton) findViewById(R.id.uber);
        Uber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    PackageManager pm = getApplicationContext().getPackageManager();
                    pm.getPackageInfo("com.ubercab", PackageManager.GET_ACTIVITIES);
                    String uri =

                            "uber://?client_id=gAuO_Frn53koJyRJLkGyL8pqgV0399_J" +
                                    "&action=setPickup" +
                                    "&pickup[latitude]=19.305917" +
                                    "&pickup[longitude]=-99.108155" +
                                    "&pickup[nickname]=Tu%20Ubicacion%20Actual" +
                                    "&dropoff[latitude]=19.376468" +
                                    "&dropoff[longitude]=-99.178189" +
                                    "&dropoff[nickname]=El%20Califa" +
                                    "&product_id=a1111c8c-c720-46c3-8534-2fcdd730040d";

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);
                } catch (PackageManager.NameNotFoundException e) {
                    // No Uber app! Open mobile website.
                    String url = "https://m.uber.com/sign-up?client_id=gAuO_Frn53koJyRJLkGyL8pqgV0399_J";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }

            }
        });
    }

    public void Navigate(View v) {

        Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }

    public void Call(View v) {

        String phone = "5538394893";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);

    }
    
    
    public void setActivity (String Titulo)
    {

        ImageView header = (ImageView) findViewById(R.id.details_image);
        TextView acerca = (TextView) findViewById(R.id.details_about);
        TextView direccion = (TextView) findViewById(R.id.details_address);
        TextView telefono = (TextView) findViewById(R.id.details_phone);
        
        switch (Titulo)
        {
            case "Don Quintin":
                this.setTitle("Don Quintin");
                direccion.setText("Tamaulipas 37-A, Cuauhtemoc.");
                header.setImageResource(R.drawable.quintin);
                acerca.setText("\nJueves de Cumpleaños! Es el dia para q lo festejes a lo grande con tus amigos. Te regalamos las burbujas, barco de perlas o redoxon y cupones para consumos en tu proxima visita. #SoloEnDQC"); 
                telefono.setText("5515233354");
                break;

            case "El Pata Negra":
                this.setTitle("El Pata Negra");
                direccion.setText("Tamaulipas 30, Cuauhtemoc.");
                header.setImageResource(R.drawable.patanegra);
                acerca.setText("\nEl Bar Legendario de la Condesa los recibe con los brazos abiertos todos los días a partir de la 13:30 p.m. Tenemos un excelente menú día a día que seguro hará que olviden la comida hecha en casa. Y para la noche una fiesta inolvidable.\n" +
                        "Pata Negra es el lugar de la Condesa que debes visitar.");
                telefono.setText("5515233354");
                break;

            case "Brooklyn Rooftop":
                this.setTitle("Brooklyn Rooftop");
                direccion.setText("San Jerónimo 263, Álvaro Obregón.");
                header.setImageResource(R.drawable.broklin);
                acerca.setText("\nCon un menú creado por el Chef Abel Hernández, el nuevo restaurante/bar Brooklyn promete ser uno de los lugares más populares del sur de la Ciudad de México.\n" +
                        " \n" +
                        "Inspirado en las mejores terrazas de la Gran Manzana y ubicado en Plaza Escenaria de San Jerónimo, este lugar ofrece buena música, comida americana contemporánea y una decoración acogedora que el despacho Arquetipo se encargó de crear.");
                telefono.setText("5515233354");
                break;

            case "El Califa Insurgentes":
                 this.setTitle("El Califa Insurgentes");
                direccion.setText("Av de los Insurgentes Sur 1217, Benito Juarez.");
                header.setImageResource(R.drawable.califa);
                acerca.setText("\nHace 20 años El Califa dio un giro al concepto tradicional de las taquerías en México creando una atmósfera joven y contemporánea, con productos de calidad y un trato personalizado.\n" +
                        "Nuestra especialidad es la elaboración y venta de tacos, distinguidos por servirse con la carne completa y tortillas hechas en el momento.");
                telefono.setText("5515233354");
                break;

            case "Matisse Polanco":
                 this.setTitle("Matisse Polanco");
                direccion.setText("Anatole France 115, Miguel Hidalgo.");
                header.setImageResource(R.drawable.matisse);
                acerca.setText("\nAbre sus puertas en 1996, como restaurante, cuando la Colonia Condesa empieza a ser el Soho de la ciudad de México. Fue una casa abandonada data de 1932, al rescatarla nosotros quisimos conservar su arquitectura original como puertas, realces en las paredes, hermosos vitrales, puertas de madera con hojas de cristal. Tiene un hermoso patio interior para desayunar, comer y cenar al aire libre sin las molestias que da estar en la calle.");
                telefono.setText("5515233354");
                break;

            case "Tennessee Condesa":
                 this.setTitle("Tennessee Condesa");
                direccion.setText("Tamaulipas 80, Cuauhtémoc.");
                header.setImageResource(R.drawable.tennessee);
                acerca.setText("\nLlegó al lugar antes ocupado por La Chopería, en Tamaulipas y Michoacán, la esquina emblemática de la Condesa, proveniente de Masaryk, donde inicialmente abrió sus puertas este grill & bar estilo Kentucky. Tennessee Condesa ofrece, junto con una agradable terraza acondicionada con mesas y sillas de madera para disfrutar del ambiente del barrio, un salón principal habilitado con mesas altas.");
                telefono.setText("5515233354");
                break;

            case "GYM 24 hrs":
                 this.setTitle("GYM 24 hrs");
                direccion.setText("Av Nuevo León 94, Cuauhtémoc.");
                header.setImageResource(R.drawable.gym24);
                acerca.setText("\nl tiempo ya no es un pretexto para no acudir a estos centros deportivos. Quien no se ajuste al horario de servicio habitual —de seis de la mañana a once de la noche— tiene el resto del día para ejercitarse, pues en estos sitios se adaptan a las agendas más apretadas y al ritmo de vida de las grandes urbes como el DF.");
                telefono.setText("5515233354");
                break;

            case "Sports World San Angel":
                 this.setTitle("Sports World San Angel");
                direccion.setText("Fernando M. Villalpando 98,Álvaro Obregón.");
                header.setImageResource(R.drawable.sports);
                acerca.setText("\nSports World Club es un club deportivo de primer nivel, equipado con lo último en aparatos y tecnología para el entrenamiento físico; es ideal para los interesados en ejercitarse de manera individual o en familia, pues ofrece actividades para todas las edades.");
                telefono.setText("5515233354");
                break;

            case "Smart Fit Polanco":
                 this.setTitle("Smart Fit Polanco");
                direccion.setText("Ejército Nacional No 350 Int L-01, Miguel Hidalgo.");
                header.setImageResource(R.drawable.smartfit);
                acerca.setText("\nEn Smart Fit te proporcionamos un ambiente agradable y relajado donde todos pueden desarrollar un estilo de vida activo y saludable sin presiones ni conflictos.\n" +
                        "Somos un medio y no un fin. Somos una herramienta para hacer tu vida mejor.\n" +
                        "Buscamos mantener un ambiente estimulante y seguro, donde puedas sentirte apreciado y respetado.");
                telefono.setText("5515233354");
                break;

            case "Xola 1413, Narvarte Poniente.":
                 this.setTitle("Renta de Departamento");
                direccion.setText("Xola 1413, Narvarte Poniente.");
                header.setImageResource(R.drawable.depa1);
                acerca.setText("\nSe renta un hermoso y cómodo departamento en el Fraccionamiento Montecristo muy bien ubicado a pocos minutos de Plaza Fiesta y de Plaza Altabrisa, cerca de iglesias, clubes deportivos y tiendas de conveniencia.\n" +
                        "\n" +
                        "El departamento se renta semi-equipado ya que cuenta con aire acondicionado, dos ventiladores de techo, estufa, mesa, closet y calentador.");
                telefono.setText("5515233354");
                break;

            case "Mérida 105, Roma Nte.":
                 this.setTitle("Renta de Departamento");
                direccion.setText("Mérida 105, Roma Nte.");
                header.setImageResource(R.drawable.depa2);
                acerca.setText("\nSe rentan departamentos totalmente amueblados en la colonia Benito Juárez Norte, muy bien ubicados al norte de la ciudad cerca del la Gran Plaza y Plaza Galerías, a pocos minutos del City Center, con rápido acceso al anillo periférico norte y a la carretera a Progreso.");
                telefono.setText("5515233354");
                break;

            case "Francisco Márquez 125, Condesa.":
                 this.setTitle("Renta de Cuarto");
                direccion.setText("Francisco Márquez 125, Condesa.");
                header.setImageResource(R.drawable.cuarto1);
                acerca.setText("\nEn Tláhuac D.F. Suites y Deptos. amueblados para 1 a 2 Profesionistas foraneos, NO AVAL, con baño propio y cocineta con loza, sartenes, refrigerador etc.\n" +
                        "\n" +
                        "Rentas desde $5,500.00 M.N. Mensual incluye: pagos de luz, gas, agua, Tv. por cable, Internet, lavado de ropa de cama, personal, aseo, estacionamiento.");
                telefono.setText("5515233354");
                break;

            case "Río Balsas 40, Cuauhtémoc.":
                 this.setTitle("Renta de Cuarto");
                direccion.setText("Río Balsas 40, Cuauhtémoc.");
                header.setImageResource(R.drawable.cuarto2);
                acerca.setText("\nSe rentan habitaciones (cama individual o matrimonial) y studios amueblados con servicios incluidos. Estamos en Santa Fe (no corporativo). a escasos minutos de Centro comercial Santa fe, Universidad Ibero, Tec de Monterrey, CIDE, Televisa Santa fe, Samara, Patio Santa Fe, centro financiero y mucho mas de los corporativos Santa Fe,. Facil acceso a transporte publico.");
                telefono.setText("5515233354");
                break;

            case "Ayudante General":
                 this.setTitle("Ayudante General");
                direccion.setText("Av Nuevo León 94, Condesa, Cuauhtémoc.");
                header.setImageResource(R.drawable.logo1);
                acerca.setText("\nEmpresa líder en su ramo solicita Asistente MEDIO TIEMPO para el área de Recursos Humanos.\n" +
                        "Actividades: Apoyar en realizar los trámites administrativos requeridos por el área, Atención directa a clientes internos y externos, Seguimiento a tareas asignadas, Manejo de agenda, Coordinación y seguimiento de mensajería, Actividades administrativas u operativas, entre otras.");
                telefono.setText("5515233354");
                break;

            case "Auxiliar administrativo":
                 this.setTitle("Auxiliar administrativo");
                direccion.setText("Chiapas 47, Cuauhtémoc.");
                header.setImageResource(R.drawable.logo2);
                acerca.setText("\nStilisimo la marca líder en salones de belleza solicita  5 Auxiliar administrativo para laborar en alguna de las diferentes sucursales ubicadas dentro del df. y area metropolitana.");
                telefono.setText("5515233354");
                break;

            case "Vendedor":
                 this.setTitle("Vendedor");
                direccion.setText("Luz Saviñón 1027, Benito Juárez.");
                header.setImageResource(R.drawable.logo3);
                acerca.setText("\n\n" +
                        "Importante Empresa de Contact Center con más de 11 Centros de Trabajo en el Distrito Federal, Estado de México, Morelos e Hidalgo, solicita por crecimiento operadores telefónicos para su campaña de ventas.");
                telefono.setText("5515233354");
                break;
        }
        
    }
}



