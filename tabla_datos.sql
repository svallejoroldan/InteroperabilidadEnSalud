-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 02-11-2020 a las 19:01:31
-- Versión del servidor: 5.7.24
-- Versión de PHP: 7.2.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `datos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tabla_datos`
--

CREATE TABLE `tabla_datos` (
  `serial` varchar(40) NOT NULL,
  `pulso` int(11) NOT NULL,
  `oxigeno` int(11) NOT NULL,
  `fecha` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tabla_datos`
--

INSERT INTO `tabla_datos` (`serial`, `pulso`, `oxigeno`, `fecha`) VALUES
('1', 30, 40, '2020-10-01'),
('11', 45, 87, '2020-12-02'),
('111', 111, 111, '2011-11-11'),
('19', 24, 47, '2020-12-12'),
('2', 44, 57, '2011-11-03'),
('222', 222, 222, '2020-12-12'),
('3', 22, 17, '2001-12-12'),
('33', 33, 33, '2020-03-03'),
('5', 60, 70, '2020-10-10'),
('55', 55, 65, '2010-10-10'),
('7', 7, 7, '2007-07-07'),
('8', 8, 8, '2008-08-08'),
('9', 9, 9, '2009-09-09'),
('90', 90, 90, '2020-01-01');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `tabla_datos`
--
ALTER TABLE `tabla_datos`
  ADD PRIMARY KEY (`serial`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
