-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 02-11-2020 a las 19:09:19
-- Versión del servidor: 10.3.16-MariaDB
-- Versión de PHP: 7.3.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `id14752472_datos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tabla_datos`
--

CREATE TABLE `tabla_datos` (
  `id` int(11) NOT NULL,
  `serial` int(11) NOT NULL,
  `pulso` int(11) NOT NULL,
  `oxigeno` int(11) NOT NULL,
  `fecha` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `tabla_datos`
--

INSERT INTO `tabla_datos` (`id`, `serial`, `pulso`, `oxigeno`, `fecha`) VALUES
(1, 20, 20, 20, '2020-10-10'),
(2, 40, 25, 211, '2020-11-11');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `tabla_datos`
--
ALTER TABLE `tabla_datos`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `tabla_datos`
--
ALTER TABLE `tabla_datos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
