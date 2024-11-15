-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 13, 2024 at 09:47 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `academic`
--

-- --------------------------------------------------------

--
-- Table structure for table `academic_years`
--

CREATE TABLE `academic_years` (
  `year_id` int(11) NOT NULL,
  `year` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `academic_years`
--

INSERT INTO `academic_years` (`year_id`, `year`) VALUES
(1, '2024'),
(2, '2024/2025'),
(3, '2024'),
(4, '2025');

-- --------------------------------------------------------

--
-- Table structure for table `attendance`
--

CREATE TABLE `attendance` (
  `attendance_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `attendance_date` date NOT NULL,
  `monday_present` tinyint(1) DEFAULT 0,
  `tuesday_present` tinyint(1) DEFAULT 0,
  `wednesday_present` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `classes`
--

CREATE TABLE `classes` (
  `class_id` int(11) NOT NULL,
  `class_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `classes`
--

INSERT INTO `classes` (`class_id`, `class_name`) VALUES
(1001, 'java2'),
(1002, 'BSCITY3S2'),
(1003, 'bssm'),
(1004, 'rrrrrr'),
(1005, 'bscity3s1'),
(1006, 'BSCITY4'),
(1007, 'BSSM');

-- --------------------------------------------------------

--
-- Table structure for table `lecturers`
--

CREATE TABLE `lecturers` (
  `lecturer_id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `employee_number` int(20) DEFAULT NULL,
  `department` varchar(100) DEFAULT NULL,
  `class_assigned` varchar(255) DEFAULT NULL,
  `module_assigned` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `lecturers`
--

INSERT INTO `lecturers` (`lecturer_id`, `name`, `employee_number`, `department`, `class_assigned`, `module_assigned`) VALUES
(2, 'thabo', 202410002, 'IT', NULL, NULL),
(6, 'lejoe', 20240004, 'IT', NULL, NULL),
(10, 'thato', 2024100, 'HR', NULL, NULL),
(12, 'thato', 20241023, 'IT', NULL, NULL),
(13, 'lerato', 20241233, 'Business', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `lecturer_modules`
--

CREATE TABLE `lecturer_modules` (
  `assignment_id` int(11) NOT NULL,
  `lecturer_id` int(11) DEFAULT NULL,
  `module_id` int(11) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  `semester_id` int(11) DEFAULT NULL,
  `year_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `lecturer_modules`
--

INSERT INTO `lecturer_modules` (`assignment_id`, `lecturer_id`, `module_id`, `class_id`, `semester_id`, `year_id`) VALUES
(1, 6, 3, 1002, NULL, NULL),
(2, 2, 3, 1002, NULL, NULL),
(3, 6, 3, 1002, NULL, NULL),
(4, 10, 3, 1002, NULL, NULL),
(5, 10, 3, 1002, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `lecturer_roles`
--

CREATE TABLE `lecturer_roles` (
  `role_id` int(11) NOT NULL,
  `lecturer_id` int(11) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `modules`
--

CREATE TABLE `modules` (
  `module_id` int(11) NOT NULL,
  `module_name` varchar(100) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `modules`
--

INSERT INTO `modules` (`module_id`, `module_name`, `code`) VALUES
(2, 'java2', '1001'),
(3, 'discrete', 'bidm123'),
(4, 'E-commerce', 'BEID123'),
(5, 'Algebra', 'AFBM1112');

-- --------------------------------------------------------

--
-- Table structure for table `reports`
--

CREATE TABLE `reports` (
  `report_id` int(11) NOT NULL,
  `employee_number` int(11) DEFAULT NULL,
  `class_name` varchar(255) DEFAULT NULL,
  `module_name` varchar(255) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  `report_date` date DEFAULT NULL,
  `challenges` text DEFAULT NULL,
  `chapter_details` text DEFAULT NULL,
  `recommendations` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reports`
--

INSERT INTO `reports` (`report_id`, `employee_number`, `class_name`, `module_name`, `class_id`, `report_date`, `challenges`, `chapter_details`, `recommendations`) VALUES
(44, 202410002, '', '', NULL, '2024-11-06', 'ba fihla late \nhaba bale', 'chapter 2', ''),
(45, 202410002, '', '', NULL, '2024-11-11', 'abscency', 'chapter1 and 2', ''),
(47, 202410002, 'BSCITY3S2', 'discrete', NULL, '2024-11-05', 'esrdtfyguhijkol', NULL, 'dszfxgchbjnk'),
(48, 202410002, 'BSCITY3S2', 'java2', NULL, '2024-11-05', 'rdtgyuihjok', NULL, 'fghjk'),
(49, 202410002, 'BSCITY3S2', 'discrete', NULL, '2024-11-12', 'zersdtbhjkm', NULL, 'weasrdtyguijkol'),
(50, 202410002, NULL, NULL, NULL, '2024-11-13', 'late comers\nthey are lazy to do work', 'chapter 1\nteach scene builder', NULL),
(51, 202410002, 'BSCITY4', 'E-commerce', NULL, '2024-11-13', 'they dont attend class', NULL, 'the need to work hard'),
(52, 202410002, NULL, NULL, NULL, '2024-11-13', 'they use drugs', 'chapter 6', NULL),
(53, 202410002, 'BSSM', 'discrete', NULL, '2024-11-13', 'they come late to class', NULL, 'they have  to arrive early and read');

-- --------------------------------------------------------

--
-- Table structure for table `semesters`
--

CREATE TABLE `semesters` (
  `semester_id` int(11) NOT NULL,
  `semester_name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `semesters`
--

INSERT INTO `semesters` (`semester_id`, `semester_name`) VALUES
(1, 'year3 s2'),
(2, 'year3s1'),
(3, 'year3s1'),
(4, 'semester2');

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `student_id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `student_number` varchar(20) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`student_id`, `name`, `student_number`, `class_id`) VALUES
(4, 'pooo', '9887727', 1001),
(6, 'pooo898', '98877270', 1001),
(7, 'sdfghj', '4567876', 1003),
(10, 'letaoa', '90101190', 1002),
(11, 'thabo', '901011080', 1001),
(12, 'teboho', '901011006', 1001);

-- --------------------------------------------------------

--
-- Table structure for table `student_modules`
--

CREATE TABLE `student_modules` (
  `student_id` int(11) NOT NULL,
  `module_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student_modules`
--

INSERT INTO `student_modules` (`student_id`, `module_id`) VALUES
(10, 3),
(10, 2),
(11, 3);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `role` enum('ADMIN','LECTURER','PRINCIPALLECTURER') NOT NULL,
  `employee_number` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `role`, `employee_number`) VALUES
(1, 'tahan', '1234', 'LECTURER', '20241005'),
(2, 'admin', '12345', 'ADMIN', '20241001'),
(3, '20241003', '20241003', 'LECTURER', '20241003'),
(4, '202410002', '202410002', 'LECTURER', '202410002'),
(5, '345678', '345678', 'LECTURER', '345678'),
(7, 'ert678', 'ert678', 'LECTURER', 'ert678'),
(8, '20240004', '20240004', 'LECTURER', '20240004'),
(9, '2024100', '2024100', 'PRINCIPALLECTURER', '2024100'),
(10, '20241023', '20241023', 'LECTURER', '20241023'),
(11, '20241233', '20241233', 'LECTURER', '20241233');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `academic_years`
--
ALTER TABLE `academic_years`
  ADD PRIMARY KEY (`year_id`);

--
-- Indexes for table `attendance`
--
ALTER TABLE `attendance`
  ADD PRIMARY KEY (`attendance_id`),
  ADD UNIQUE KEY `student_id` (`student_id`,`attendance_date`);

--
-- Indexes for table `classes`
--
ALTER TABLE `classes`
  ADD PRIMARY KEY (`class_id`);

--
-- Indexes for table `lecturers`
--
ALTER TABLE `lecturers`
  ADD PRIMARY KEY (`lecturer_id`),
  ADD UNIQUE KEY `employee_number` (`employee_number`);

--
-- Indexes for table `lecturer_modules`
--
ALTER TABLE `lecturer_modules`
  ADD PRIMARY KEY (`assignment_id`),
  ADD KEY `lecturer_id` (`lecturer_id`),
  ADD KEY `module_id` (`module_id`),
  ADD KEY `class_id` (`class_id`),
  ADD KEY `semester_id` (`semester_id`),
  ADD KEY `year_id` (`year_id`);

--
-- Indexes for table `lecturer_roles`
--
ALTER TABLE `lecturer_roles`
  ADD PRIMARY KEY (`role_id`),
  ADD KEY `lecturer_id` (`lecturer_id`);

--
-- Indexes for table `modules`
--
ALTER TABLE `modules`
  ADD PRIMARY KEY (`module_id`),
  ADD UNIQUE KEY `code` (`code`);

--
-- Indexes for table `reports`
--
ALTER TABLE `reports`
  ADD PRIMARY KEY (`report_id`),
  ADD KEY `reports_ibfk_1` (`employee_number`),
  ADD KEY `fk_reports_class_id` (`class_id`);

--
-- Indexes for table `semesters`
--
ALTER TABLE `semesters`
  ADD PRIMARY KEY (`semester_id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`student_id`),
  ADD UNIQUE KEY `student_number` (`student_number`),
  ADD KEY `class_id` (`class_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `employee_number` (`employee_number`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `academic_years`
--
ALTER TABLE `academic_years`
  MODIFY `year_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `attendance`
--
ALTER TABLE `attendance`
  MODIFY `attendance_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `classes`
--
ALTER TABLE `classes`
  MODIFY `class_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1008;

--
-- AUTO_INCREMENT for table `lecturers`
--
ALTER TABLE `lecturers`
  MODIFY `lecturer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `lecturer_modules`
--
ALTER TABLE `lecturer_modules`
  MODIFY `assignment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `lecturer_roles`
--
ALTER TABLE `lecturer_roles`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `modules`
--
ALTER TABLE `modules`
  MODIFY `module_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `reports`
--
ALTER TABLE `reports`
  MODIFY `report_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;

--
-- AUTO_INCREMENT for table `semesters`
--
ALTER TABLE `semesters`
  MODIFY `semester_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `student_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `attendance`
--
ALTER TABLE `attendance`
  ADD CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`) ON DELETE CASCADE;

--
-- Constraints for table `lecturer_modules`
--
ALTER TABLE `lecturer_modules`
  ADD CONSTRAINT `lecturer_modules_ibfk_1` FOREIGN KEY (`lecturer_id`) REFERENCES `lecturers` (`lecturer_id`),
  ADD CONSTRAINT `lecturer_modules_ibfk_2` FOREIGN KEY (`module_id`) REFERENCES `modules` (`module_id`),
  ADD CONSTRAINT `lecturer_modules_ibfk_3` FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`),
  ADD CONSTRAINT `lecturer_modules_ibfk_4` FOREIGN KEY (`semester_id`) REFERENCES `semesters` (`semester_id`),
  ADD CONSTRAINT `lecturer_modules_ibfk_5` FOREIGN KEY (`year_id`) REFERENCES `academic_years` (`year_id`);

--
-- Constraints for table `lecturer_roles`
--
ALTER TABLE `lecturer_roles`
  ADD CONSTRAINT `lecturer_roles_ibfk_1` FOREIGN KEY (`lecturer_id`) REFERENCES `lecturers` (`lecturer_id`);

--
-- Constraints for table `reports`
--
ALTER TABLE `reports`
  ADD CONSTRAINT `fk_reports_class_id` FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`),
  ADD CONSTRAINT `reports_ibfk_1` FOREIGN KEY (`employee_number`) REFERENCES `lecturers` (`employee_number`);

--
-- Constraints for table `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `students_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `classes` (`class_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
