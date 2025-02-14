<?php
// Importar PHPMailer
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

// Incluir las dependencias de PHPMailer
require 'vendor/autoload.php';

// Habilitar reporte de errores en PHP (para depuración)
error_reporting(E_ALL);
ini_set('display_errors', 1);

// Configurar encabezados para permitir CORS y asegurarse de que la respuesta sea JSON
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST, GET, OPTIONS");
header("Access-Control-Allow-Headers: Content-Type");
header("Content-Type: application/json; charset=UTF-8");

// Solo continuar si el método es POST
if ($_SERVER["REQUEST_METHOD"] !== "POST") {
    echo json_encode(["status" => "error", "message" => "Método no permitido"]);
    exit;
}

// Capturar los datos JSON
$datosCrudos = file_get_contents("php://input");
error_log("Datos recibidos: " . $datosCrudos); // Log para depuración
$datos = json_decode($datosCrudos, true);

// Validar si el JSON se decodificó correctamente
if ($datos === null) {
    echo json_encode(["status" => "error", "message" => "Error al decodificar JSON", "data" => $datosCrudos]);
    exit;
}

// Extraer los datos
$nombre = $datos['nombre'] ?? '';
$apellido = $datos['apellido'] ?? '';
$correo = $datos['correo'] ?? '';
$direccion = $datos['direccion'] ?? '';
$dni = $datos['dni'] ?? '';
$producto = $datos['producto'] ?? '';
$precio = $datos['precio'] ?? '';
$descripcion = $datos['descripcion'] ?? '';

// Validación de datos obligatorios
if (empty($nombre) || empty($apellido) || empty($correo) || empty($direccion) || empty($dni)) {
    echo json_encode(["status" => "error", "message" => "Todos los campos son obligatorios"]);
    exit;
}

// Crear instancia de PHPMailer
$mail = new PHPMailer(true);

// Crear instancia de PHPMailer
$mail = new PHPMailer(true);

try {
    // Configurar servidor SMTP (Usando Gmail como ejemplo)
    $mail->isSMTP();
    $mail->Host = 'smtp.gmail.com'; 
    $mail->SMTPAuth = true;
    $mail->Username = '';
    $mail->Password = '';
    $mail->SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS;
    $mail->Port = 587;

    // Configuración del correo
    $mail->setFrom('', 'Bazar Chino');
    $mail->addAddress($correo); // Enviar al usuario
    

 // Contenido del correo
 $mail->isHTML(true);
 $mail->Subject = "Confirmacion de compra: $producto";
 $mail->Body = "
    <html>
    <head>
        <title>Confirmación de compra</title>
    </head>
    <body>
        <h2>¡Gracias por tu compra, $nombre $apellido!</h2>
        <p><strong>Producto:</strong> $producto</p>
        <p><strong>Precio:</strong> $precio €</p>
        <p><strong>Descripción:</strong> $descripcion</p>
        <p><strong>Dirección de envío:</strong> $direccion</p>
        <p><strong>DNI:</strong> $dni</p>
        <img src='https://media.giphy.com/media/TjTKLTQQkODba/giphy.gif' alt='Gracias por tu compra' width='250px'/>
        <br>
        <p>Nos pondremos en contacto contigo para más detalles.</p>
        <br>
        
    </body>
    </html>
";


 // Enviar correo
 if ($mail->send()) {
     echo json_encode(["status" => "success", "message" => "Correo enviado correctamente"]);
 } else {
     echo json_encode(["status" => "error", "message" => "Error al enviar el correo: " . $mail->ErrorInfo]);
 }
} catch (Exception $e) {
 echo json_encode(["status" => "error", "message" => "Error de PHPMailer: " . $mail->ErrorInfo]);
}


?>
