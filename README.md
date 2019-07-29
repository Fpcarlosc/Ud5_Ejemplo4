# Ud5_Ejemplo4
_Ejemplo 4 de la Unidad 5._ 

Vamos a implementar una apliación en la que haremos uso de _ListFragment_. De esta clase heredará el _Fragment_ principal que contendrá 
un elemento _ListView_ con diferentes nombres de robots famosos. Al pulsar en uno de ellos mostraremos su nombre otro _Fragment_.

Gracias a que vamos a utilizar _Fragments_ podemos implementar el ejemplo, a la vez, tanto para el dispositivo en posición normal, donde
 pasaremos de un _Fragment_ a otro, como rotado (o para una tablet), de tal forma que se verán los dos _Fragments_ al mismo tiempo.
 
 Para ello tenemos que seguir los siguientes pasos:
 
 ## Paso 1: Creación de los _layouts_
 
El primer paso será crear los _layouts_ tanto para el dipositivo en posición normal como rotado.

### Dispositivo en posición normal
Para la posición normal del dispositivo tenemos los siguientes _layouts_:

_activity_main.xml_:
```
<android.support.constraint.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <FrameLayout
        android:id="@+id/frameLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginTop="8dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</android.support.constraint.ConstraintLayout>
```
Donde simplemente tendremos un _FrameLayout_ para posición la lista.

_fragment_detalle.xml_:
```
<android.support.constraint.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/textViewNombre"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</android.support.constraint.ConstraintLayout>
```
Con un solo _TextView_ para mostrar el nombre del robot.

### Dispositivo rotado
Para crear el _layout_ del dispositivo rotado, primero creamos una nueva carpeta llamada _layout-land_ dentro del directorio _res_.

Si no aparece el directorio _res/layout-land_ en el árbol de directorios, cambiad la vista de _Android_ a _Project_.

Una vez creada el directorio, dentro de éste crearemos el fichero _activity_main.xml_:
```
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal">
    <FrameLayout
        android:id="@+id/frameLayoutPrincipal"
        android:layout_width="0dp"
        android:layout_weight="1"
        android:layout_height="match_parent"/>
    <FrameLayout
        android:id="@+id/frameLayoutDetalle"
        android:layout_width="0dp"
        android:layout_weight="1"
        android:layout_height="match_parent"/>
</LinearLayout>
```
Para esta posición tendremos un único _layout_ con dos _FrameLayout_ uno para la lista y otro para el detalle del nombre. Ambos 
están dentro de un _LinearLayout_ y tendrán el mismo peso en pantalla.

## Creación de los _Fragments_

Tendremos dos _Fragments_ uno para la lista y otro para mostrar el nombre del robot seleccionado.

_FragmentPrincipal.java_:
```
public class FragmentPrincipal extends ListFragment {

    private OnPrincipalSelectedListener onPrincipalSelectedListener = null;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] robots = new String[]{"R2D2", "Mazinger Z", "Arale", "Robocop", "Johnny 5", "Blender", "T-800"};

        //Cramos el ListAdapter y se lo asignamos al ListFragment
        ListAdapter robotsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, robots);

        setListAdapter(robotsAdapter);

        // Obtenemos la ListView y le asignamos el evento
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View
                    view, int position, long id) {
                if (onPrincipalSelectedListener != null) {
                    onPrincipalSelectedListener.onItemSelected(((TextView) view).getText().toString());
                }
            }
        });
    }

    /**
     * Método para asignar el Listener al pulsar un elemento de FramentPrincipal.
     * @param listener
     */
    public void setOnPrincipalSelectedListener(OnPrincipalSelectedListener listener) {
        onPrincipalSelectedListener = listener;
    }

    /**
     * Interfaz para controlar los elementos pulsados de FragmentPrincipal
     */
    public interface OnPrincipalSelectedListener {
        void onItemSelected(String nombre);
    }
}
```
Éste será el _Fragment_ que implementará la lista. Para ello creamos un _array_ de Strings con los nombres de los robots, 
creamos el _ListAdapter_, se lo asignamos al _ListFragment_ y obtenemos la _ListView_ asociada y le asignamos el evento.
 Para asignárselo, primero deberemos de crear una interfaz que controle los elementos pulsados del _Fragment_ (_OnPrincipalSelectedListener_) y un método para asignar 
 el _Listener_ (_OnPrincipalSelectedListener_).
 
 _FragmentDetalle.java_:
 ```
 public class FragmentDetalle extends Fragment {
    // Clave para crear el bundle.
    public static String CLAVE_NOMBRE = "CLAVE_NOMBRE";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtenemos el bundle y si es correcto mostramos el robot seleccionado
        Bundle bundle = getArguments();

        if (bundle != null && bundle.containsKey(CLAVE_NOMBRE)) {
            // Obtenemos el nombre en función de la clave y lo pasamos al método
            mostrarRobotSeleccionado(bundle.getString(CLAVE_NOMBRE));
        }
    }

    public void mostrarRobotSeleccionado(String nombre) {
        ((TextView)getView().findViewById(R.id.textViewNombre)).setText(nombre);
    }
}
```
En este _Fragment_ es donde mostraremos el nombre del robot seleccionado. Para ello tendremos que sobreescribir dos métodos: _onCreateView_ 
para "inflar" el _layout_ del _Fragment_ y _onViewCreated_ para crear el _bundle_ y mostrar el nombre (_mostrarRobotSeleccionado_) en función de la clave. 

## Implementación de _MainActivity.java_
El contenido de la actividad principal será el siguiente:
```
public class MainActivity extends AppCompatActivity {

    private boolean rotado; // Atributo para ver si el dispositivo está rotado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentPrincipal fragmentPrincipal;

        FrameLayout frameLayout = findViewById(R.id.frameLayout);

        // Si framLayout tiene valor es porque el dispositivo no está rotado
        if (frameLayout != null) {
            rotado = false;

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            //Buscamos el Fragment por el tag "PRINCIPAL"
            fragmentPrincipal = (FragmentPrincipal) getSupportFragmentManager().findFragmentByTag("PRINCIPAL");

            // Si no lo encontramos lo añadimos a la transacción con el tag "PRINCIPAL"
            if (fragmentPrincipal == null) {
                fragmentPrincipal = new FragmentPrincipal();
                fragmentTransaction.add(R.id.frameLayout, fragmentPrincipal, "PRINCIPAL");
            }

            //Buscamos el Fragment por el id
            FragmentDetalle fragmentDetalle = (FragmentDetalle)getSupportFragmentManager().findFragmentById(R.id.frameLayoutDetalle);

            // Si está mostrado lo borramos
            if (fragmentDetalle != null) {
                fragmentTransaction.remove(fragmentDetalle);
            }

            fragmentTransaction.commit();
        }
        else {

            rotado = true;

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            //Buscamos el Fragment por el id
            fragmentPrincipal = (FragmentPrincipal)getSupportFragmentManager().findFragmentById(R.id.frameLayoutPrincipal);

            // Si no lo encontramos lo añadimos a la transacción
            if (fragmentPrincipal == null) {
                fragmentPrincipal = new FragmentPrincipal();
                fragmentTransaction.add(R.id.frameLayoutPrincipal, fragmentPrincipal);
            }

            //Buscamos el Fragment por el id
            FragmentDetalle fragmentDetalle= (FragmentDetalle) getSupportFragmentManager().findFragmentById(R.id.frameLayoutDetalle);

            // Si no lo encontramos lo añadimos a la transacción
            if (fragmentDetalle == null) {
                fragmentDetalle = new FragmentDetalle();
                fragmentTransaction.add(R.id.frameLayoutDetalle, fragmentDetalle);
            }

            fragmentTransaction.commit();
        }

        // Asignamos el Listener creado en la clase FragmentPrincipal
        fragmentPrincipal.setOnPrincipalSelectedListener(new FragmentPrincipal.OnPrincipalSelectedListener() {
            @Override
            public void onItemSelected(String nombre) {
                enviarNombre(nombre);
            }
        });

    }

    /**
     * Método para enviar el nombre a los Fragments y mostrarlo
     * @param nombre
     */
    private void enviarNombre(String nombre) {

        FragmentDetalle fragmentDetalle;

        if (rotado) {
            //Mostramos también el detalle
            fragmentDetalle = (FragmentDetalle) getSupportFragmentManager().findFragmentById(R.id.frameLayoutDetalle);

            fragmentDetalle.mostrarRobotSeleccionado(nombre);
        }
        else {
            // Obtenemos el nombre a partir de la clave y reemplazamos FragmentPrincipal por FragmentDetalle
            fragmentDetalle = new FragmentDetalle();

            // Creamos el bundle con la clave y el nombre pasado
            Bundle bundle = new Bundle();
            bundle.putString(FragmentDetalle.CLAVE_NOMBRE, nombre);

            fragmentDetalle.setArguments(bundle);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, fragmentDetalle);

            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
```
En esta actividad tendremos dos casos:
1. El dispositivo está en posición normal: Donde buscaremos los _Fragments_ (el principal por la etiqueta y el detalle por el _id_) e
 iremos añadiendo o eliminando en la transacción dependiendo de cuál de los dos debe ser mostrado.
2. El dispositivo está rotado: En este caso añadimos los dos _Fragments_ a la transacción para mostrarlos a la vez.

Para ambos casos asignamos un _Listener_ que muestre el nombre en _FragmentDetalle_, si está rotado simplemente lo mostrarmos y sino 
creamos el _bundle_ y lo reemplazamos en la transacción.
