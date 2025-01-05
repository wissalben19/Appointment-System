-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 28, 2023 at 12:29 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hospiflow`
--

-- --------------------------------------------------------

--
-- Table structure for table `appiontment`
--

CREATE TABLE `appiontment` (
  `id` int(11) NOT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `datein` date DEFAULT NULL,
  `timein` time DEFAULT NULL,
  `doctor_id` int(11) DEFAULT NULL,
  `selectdoctor` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `appiontment`
--

INSERT INTO `appiontment` (`id`, `firstname`, `lastname`, `age`, `gender`, `datein`, `timein`, `doctor_id`, `selectdoctor`) VALUES
(1, 'wisal', 'ben', 13, 'Female', '2023-12-21', '23:25:00', NULL, ''),
(2, 'wisal', 'ben', 13, 'Female', '2023-12-21', '12:25:00', NULL, ''),
(3, 'wissal', 'ben', 19, 'Female', '2023-12-28', '00:10:00', NULL, 'Dr. wissal ben'),
(4, 'wissal', 'ben', 19, 'Female', '2023-12-28', '01:10:00', NULL, 'Dr. wissal ben'),
(5, 'wissal', 'ben', 19, 'Female', '2023-12-21', '00:21:00', NULL, 'Dr. wissal ben');

-- --------------------------------------------------------

--
-- Table structure for table `doctorlogin`
--

CREATE TABLE `doctorlogin` (
  `doctor_id` int(40) NOT NULL,
  `firstnameD` varchar(30) NOT NULL,
  `lastnameD` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `phone` varchar(30) NOT NULL,
  `genderD` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `doctorlogin`
--

INSERT INTO `doctorlogin` (`doctor_id`, `firstnameD`, `lastnameD`, `email`, `password`, `phone`, `genderD`) VALUES
(2, 'wissal', 'ben', 'wissalben@gmai.com', '123qwe', '0987654321', 'Female');

--
-- Triggers `doctorlogin`
--
DELIMITER $$
CREATE TRIGGER `after_doctorlogin_insert` AFTER INSERT ON `doctorlogin` FOR EACH ROW BEGIN
    -- Insert into loginpage table with user type set to 'doctor'
    INSERT INTO loginpage (email, password, user_type)
    VALUES (NEW.email, NEW.password, 'Doctor');
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `doctorprofile`
--

CREATE TABLE `doctorprofile` (
  `doctor_id` int(40) NOT NULL,
  `firstnameD` varchar(30) NOT NULL,
  `lastnameD` varchar(30) NOT NULL,
  `address` varchar(255) NOT NULL,
  `email` varchar(30) NOT NULL,
  `phone` varchar(30) NOT NULL,
  `genderD` varchar(30) NOT NULL,
  `workdays` tinyint(1) NOT NULL,
  `specialty` varchar(300) NOT NULL,
  `photo_path` varchar(255) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `loginpage`
--

CREATE TABLE `loginpage` (
  `login_id` int(40) NOT NULL,
  `email` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `user_type` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `loginpage`
--

INSERT INTO `loginpage` (`login_id`, `email`, `password`, `user_type`) VALUES
(1, 'wissalbenzahi@gmail.com', '12341234', 'Doctor'),
(3, 'wwwwwww@gmail.com', 'rrrr44444', 'Patient'),
(4, '333ddddd@gmail.com', '1111ww', 'Receptionist'),
(5, 'wissalben@gmai.com', '123qwe', 'Doctor'),
(6, 'asmaben@gmail.com', '98765432', 'Receptionist'),
(7, 'ramialae@gmail.com', '202312q', 'Patient');

-- --------------------------------------------------------

--
-- Table structure for table `patientregistration`
--

CREATE TABLE `patientregistration` (
  `patient_id` int(11) NOT NULL,
  `firstnameP` varchar(30) NOT NULL,
  `lastnameP` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `genderP` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `patientregistration`
--

INSERT INTO `patientregistration` (`patient_id`, `firstnameP`, `lastnameP`, `email`, `password`, `genderP`) VALUES
(2, 'rrrrrrrr', 'rrrrrrrrr', 'wwwwwww@gmail.com', 'rrrr44444', 'Female'),
(3, 'alae', 'rami', 'ramialae@gmail.com', '202312q', 'Male');

--
-- Triggers `patientregistration`
--
DELIMITER $$
CREATE TRIGGER `after_patient_insert` AFTER INSERT ON `patientregistration` FOR EACH ROW BEGIN
    -- Insert into loginpage table
    INSERT INTO loginpage (email, password, user_type)
    VALUES (NEW.email, NEW.password, 'Patient');
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `patients`
--

CREATE TABLE `patients` (
  `id` int(11) NOT NULL,
  `first_name` text DEFAULT NULL,
  `last_name` text DEFAULT NULL,
  `phone_number` text DEFAULT NULL,
  `date_of_birth` text DEFAULT NULL,
  `gender` text DEFAULT NULL,
  `disease` text DEFAULT NULL,
  `additional_info` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patients`
--

INSERT INTO `patients` (`id`, `first_name`, `last_name`, `phone_number`, `date_of_birth`, `gender`, `disease`, `additional_info`) VALUES
(1, 'wissal', 'ben', '12345678', '2023-12-30', 'Unknown', '', ''),
(2, 'wissal', 'ben', '12345678', '2023-12-30', 'Unknown', '', 'qqqqqq'),
(3, 'wissal', 'ben', '12345678', '2023-12-30', 'Unknown', '', 'qqqqqq'),
(4, 'wissal', 'benzahi', '12345678', '2004-06-05', 'Unknown', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `receptionnistelogin`
--

CREATE TABLE `receptionnistelogin` (
  `receptionniste_id` int(11) NOT NULL,
  `firstnameR` varchar(30) NOT NULL,
  `lastnameR` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `receptionnistelogin`
--

INSERT INTO `receptionnistelogin` (`receptionniste_id`, `firstnameR`, `lastnameR`, `email`, `password`) VALUES
(1, 'ffffff', 'ffffff', '333ddddd@gmail.com', '1111ww'),
(2, 'asma', 'ben', 'asmaben@gmail.com', '98765432');

--
-- Triggers `receptionnistelogin`
--
DELIMITER $$
CREATE TRIGGER `after_insert_receptionnistelogin` AFTER INSERT ON `receptionnistelogin` FOR EACH ROW BEGIN
    -- Insert into loginpage with user type Receptionist
    INSERT INTO loginpage (email, password, user_type)
    VALUES (NEW.email, NEW.password, 'Receptionist');
END
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `appiontment`
--
ALTER TABLE `appiontment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `doctor_id` (`doctor_id`),
  ADD KEY `fk_doctor` (`selectdoctor`);

--
-- Indexes for table `doctorlogin`
--
ALTER TABLE `doctorlogin`
  ADD PRIMARY KEY (`doctor_id`);

--
-- Indexes for table `doctorprofile`
--
ALTER TABLE `doctorprofile`
  ADD PRIMARY KEY (`doctor_id`);

--
-- Indexes for table `loginpage`
--
ALTER TABLE `loginpage`
  ADD PRIMARY KEY (`login_id`);

--
-- Indexes for table `patientregistration`
--
ALTER TABLE `patientregistration`
  ADD PRIMARY KEY (`patient_id`);

--
-- Indexes for table `patients`
--
ALTER TABLE `patients`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `receptionnistelogin`
--
ALTER TABLE `receptionnistelogin`
  ADD PRIMARY KEY (`receptionniste_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `appiontment`
--
ALTER TABLE `appiontment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `doctorlogin`
--
ALTER TABLE `doctorlogin`
  MODIFY `doctor_id` int(40) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `loginpage`
--
ALTER TABLE `loginpage`
  MODIFY `login_id` int(40) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `patientregistration`
--
ALTER TABLE `patientregistration`
  MODIFY `patient_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `patients`
--
ALTER TABLE `patients`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `receptionnistelogin`
--
ALTER TABLE `receptionnistelogin`
  MODIFY `receptionniste_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;


--
-- Metadata
--
USE `phpmyadmin`;

--
-- Metadata for table appiontment
--

--
-- Metadata for table doctorlogin
--

--
-- Metadata for table doctorprofile
--

--
-- Metadata for table loginpage
--

--
-- Metadata for table patientregistration
--

--
-- Metadata for table patients
--

--
-- Metadata for table receptionnistelogin
--

--
-- Metadata for database hospiflow
--
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
