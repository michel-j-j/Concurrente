# Simulación del Proceso de Minería en Blockchain Usando Concurrencia en Java

## Descripción del Proyecto  
Este proyecto es una simulación del proceso de minería en una **blockchain**, implementada en Java. El objetivo es modelar cómo múltiples *mineros* (representados por hilos) compiten para validar y agregar transacciones a un bloque, utilizando un mecanismo de prueba de trabajo (**Proof-of-Work**). La simulación incluye elementos de programación concurrente, como sincronización de hilos, para garantizar la integridad y el correcto funcionamiento de la blockchain.

## Características Principales  
- **Mineros concurrentes:** Varios hilos representan a los mineros, que realizan la prueba de trabajo para encontrar un hash válido.
- **Prueba de trabajo (Proof-of-Work):** Cada minero calcula hashes modificando un valor (*nonce*) hasta cumplir con la dificultad requerida (número de ceros iniciales en el hash).
- **Blockchain sincronizada:** Se asegura que solo un minero pueda agregar un bloque a la blockchain mediante mecanismos de sincronización.
- **Interfaz gráfica (GUI):** Visualiza el estado de los mineros, el proceso de minería y muestra el minero ganador.
- **Mecanismos de sincronización:** Uso de programación concurrente para gestionar las secciones críticas, evitando inconsistencias en la cadena.

## Estructura del Proyecto  
El proyecto está organizado en los siguientes paquetes y clases:

### **Paquete `model`**  
Contiene las clases principales que definen el comportamiento de la blockchain y los mineros:
- **`Blockchain`:** Representa la blockchain como una lista de bloques y contiene métodos para agregar y validar bloques.
- **`Block`:** Representa un bloque individual de la blockchain, con información como el hash, el hash del bloque anterior, el nonce y el identificador del minero.
- **`Miner`:** Clase que extiende `Thread`, representa a un minero que realiza la prueba de trabajo y compite para agregar bloques.

### **Paquete `gui`**  
Incluye la interfaz gráfica para visualizar el proceso de minería:
- **`BlockchainMiningGUI`:** Proporciona una representación visual del proceso, mostrando los intentos de los mineros, los mineros confirmando bloques y el minero ganador.

## Ejecución del Proyecto  

### **Requisitos Previos**  
- **Java 8** o superior.
- IDE como IntelliJ IDEA o Eclipse (opcional, pero recomendado).
- Conocimientos básicos de programación concurrente en Java.

### **Instrucciones de Uso**  
1. Clona este repositorio en tu máquina local.
2. Compila y ejecuta el archivo principal ubicado en la clase `BlockchainMiningGUI`:
   ```java
   public static void main(String[] args) {
       SwingUtilities.invokeLater(() -> {
           Blockchain blockchain = new Blockchain();
           BlockchainMiningGUI gui = new BlockchainMiningGUI(blockchain);
           gui.startMining();
       });
   }
   ```
3. La simulación se abrirá en una ventana gráfica donde podrás:
   - Observar los intentos de los mineros en tiempo real.
   - Ver los mineros confirmando bloques.
   - Identificar al minero ganador.

### **Funcionamiento del Proceso de Minería**  
1. Se inicializa una blockchain con un bloque génesis.
2. Cada minero intenta encontrar un hash que cumpla con la dificultad establecida (cuatro ceros iniciales).
3. El primer minero que logra encontrar un hash válido intenta agregar el bloque a la blockchain.
4. La sincronización garantiza que solo un minero pueda agregar el bloque mientras los demás validan el resultado.
5. El bloque agregado incluye información sobre el minero que lo encontró.

## Temas de Programación Concurrente Aplicados  
- **Secciones Críticas:** Solo un minero puede agregar un bloque a la blockchain.
- **Sincronización:** Uso del modificador `synchronized` para proteger la integridad de la cadena.
- **Paralelismo:** Uso de hilos para simular mineros compitiendo simultáneamente.
- **Comunicación entre hilos:** Interacción entre los hilos y la GUI para actualizar el estado de los mineros y el resultado.

## Personalización  
- **Dificultad del Proof-of-Work:** Modifica el valor de `DIFFICULTY` en la clase `Miner` para ajustar la cantidad de ceros iniciales requeridos en el hash.
- **Número de mineros:** Cambia el tamaño del `ExecutorService` en `BlockchainMiningGUI` para simular más o menos mineros.

## Ejemplo de Salida  
En la GUI se mostrará:
- Los intentos de los mineros con información sobre el nonce y el hash generado.
- El bloque confirmado y el minero que lo validó.
- Un mensaje indicando al minero ganador.

## Contribución  
Si tienes ideas para mejorar este proyecto o quieres reportar errores, ¡eres bienvenido a contribuir! Crea un *fork* del repositorio y envía un *pull request* con tus cambios.

## Licencia  
Este proyecto se distribuye bajo la licencia **MIT**. Siéntete libre de usarlo, modificarlo y distribuirlo.

---

¡Gracias por explorar esta simulación de minería en blockchain! 🎉
