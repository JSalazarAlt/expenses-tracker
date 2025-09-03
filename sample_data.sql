-- Sample expense data from January 2024 to August 2025
-- Fixed rent: $400/month, Variable food: ~$200/month, Variable utilities and transportation

-- 2024 Data
INSERT INTO expenses (expense_description, expense_amount, expense_date, expense_category, created_at, updated_at) VALUES
-- January 2024
('Monthly Rent', 400.00, '2024-01-01', 'HOUSING', NOW(), NOW()),
('Grocery Shopping', 185.50, '2024-01-03', 'FOOD', NOW(), NOW()),
('Gas Bill', 85.20, '2024-01-05', 'UTILITIES', NOW(), NOW()),
('Bus Pass', 45.00, '2024-01-07', 'TRANSPORTATION', NOW(), NOW()),
('Restaurant Dinner', 32.75, '2024-01-12', 'FOOD', NOW(), NOW()),
('Electricity Bill', 120.30, '2024-01-15', 'UTILITIES', NOW(), NOW()),
('Gasoline', 55.80, '2024-01-18', 'TRANSPORTATION', NOW(), NOW()),
('Groceries', 78.90, '2024-01-22', 'FOOD', NOW(), NOW()),
('Internet Bill', 65.00, '2024-01-25', 'UTILITIES', NOW(), NOW()),

-- February 2024
('Monthly Rent', 400.00, '2024-02-01', 'HOUSING', NOW(), NOW()),
('Grocery Shopping', 195.25, '2024-02-04', 'FOOD', NOW(), NOW()),
('Water Bill', 42.15, '2024-02-06', 'UTILITIES', NOW(), NOW()),
('Uber Ride', 18.50, '2024-02-08', 'TRANSPORTATION', NOW(), NOW()),
('Coffee Shop', 12.40, '2024-02-10', 'FOOD', NOW(), NOW()),
('Gas Bill', 92.80, '2024-02-14', 'UTILITIES', NOW(), NOW()),
('Gasoline', 62.30, '2024-02-16', 'TRANSPORTATION', NOW(), NOW()),
('Takeout Food', 28.75, '2024-02-20', 'FOOD', NOW(), NOW()),
('Phone Bill', 55.00, '2024-02-25', 'UTILITIES', NOW(), NOW()),

-- March 2024
('Monthly Rent', 400.00, '2024-03-01', 'HOUSING', NOW(), NOW()),
('Grocery Shopping', 210.80, '2024-03-05', 'FOOD', NOW(), NOW()),
('Electricity Bill', 135.45, '2024-03-07', 'UTILITIES', NOW(), NOW()),
('Bus Ticket', 8.25, '2024-03-09', 'TRANSPORTATION', NOW(), NOW()),
('Lunch Out', 15.60, '2024-03-12', 'FOOD', NOW(), NOW()),
('Internet Bill', 65.00, '2024-03-15', 'UTILITIES', NOW(), NOW()),
('Car Maintenance', 125.00, '2024-03-18', 'TRANSPORTATION', NOW(), NOW()),
('Groceries', 89.30, '2024-03-22', 'FOOD', NOW(), NOW()),
('Gas Bill', 78.90, '2024-03-28', 'UTILITIES', NOW(), NOW()),

-- April 2024
('Monthly Rent', 400.00, '2024-04-01', 'HOUSING', NOW(), NOW()),
('Grocery Shopping', 188.75, '2024-04-03', 'FOOD', NOW(), NOW()),
('Water Bill', 38.20, '2024-04-05', 'UTILITIES', NOW(), NOW()),
('Gasoline', 58.40, '2024-04-08', 'TRANSPORTATION', NOW(), NOW()),
('Restaurant', 45.80, '2024-04-11', 'FOOD', NOW(), NOW()),
('Electricity Bill', 115.60, '2024-04-14', 'UTILITIES', NOW(), NOW()),
('Taxi Ride', 22.30, '2024-04-17', 'TRANSPORTATION', NOW(), NOW()),
('Groceries', 95.20, '2024-04-21', 'FOOD', NOW(), NOW()),
('Phone Bill', 55.00, '2024-04-25', 'UTILITIES', NOW(), NOW()),

-- May 2024
('Monthly Rent', 400.00, '2024-05-01', 'HOUSING', NOW(), NOW()),
('Grocery Shopping', 205.40, '2024-05-04', 'FOOD', NOW(), NOW()),
('Gas Bill', 68.75, '2024-05-06', 'UTILITIES', NOW(), NOW()),
('Bus Pass', 45.00, '2024-05-08', 'TRANSPORTATION', NOW(), NOW()),
('Fast Food', 18.90, '2024-05-12', 'FOOD', NOW(), NOW()),
('Internet Bill', 65.00, '2024-05-15', 'UTILITIES', NOW(), NOW()),
('Gasoline', 64.20, '2024-05-18', 'TRANSPORTATION', NOW(), NOW()),
('Groceries', 82.15, '2024-05-23', 'FOOD', NOW(), NOW()),
('Electricity Bill', 128.30, '2024-05-28', 'UTILITIES', NOW(), NOW()),

-- June 2024
('Monthly Rent', 400.00, '2024-06-01', 'HOUSING', NOW(), NOW()),
('Grocery Shopping', 192.60, '2024-06-03', 'FOOD', NOW(), NOW()),
('Water Bill', 44.80, '2024-06-05', 'UTILITIES', NOW(), NOW()),
('Uber Ride', 25.70, '2024-06-08', 'TRANSPORTATION', NOW(), NOW()),
('Dinner Out', 38.50, '2024-06-11', 'FOOD', NOW(), NOW()),
('Gas Bill', 72.40, '2024-06-14', 'UTILITIES', NOW(), NOW()),
('Gasoline', 59.80, '2024-06-17', 'TRANSPORTATION', NOW(), NOW()),
('Groceries', 76.90, '2024-06-22', 'FOOD', NOW(), NOW()),
('Phone Bill', 55.00, '2024-06-25', 'UTILITIES', NOW(), NOW()),

-- July 2024
('Monthly Rent', 400.00, '2024-07-01', 'HOUSING', NOW(), NOW()),
('Grocery Shopping', 218.30, '2024-07-04', 'FOOD', NOW(), NOW()),
('Electricity Bill', 145.20, '2024-07-07', 'UTILITIES', NOW(), NOW()),
('Bus Ticket', 12.50, '2024-07-09', 'TRANSPORTATION', NOW(), NOW()),
('Ice Cream', 8.75, '2024-07-12', 'FOOD', NOW(), NOW()),
('Internet Bill', 65.00, '2024-07-15', 'UTILITIES', NOW(), NOW()),
('Gasoline', 67.40, '2024-07-18', 'TRANSPORTATION', NOW(), NOW()),
('Groceries', 91.80, '2024-07-23', 'FOOD', NOW(), NOW()),
('Water Bill', 41.60, '2024-07-28', 'UTILITIES', NOW(), NOW()),

-- August 2024
('Monthly Rent', 400.00, '2024-08-01', 'HOUSING', NOW(), NOW()),
('Grocery Shopping', 201.85, '2024-08-03', 'FOOD', NOW(), NOW()),
('Gas Bill', 58.90, '2024-08-05', 'UTILITIES', NOW(), NOW()),
('Taxi Ride', 19.40, '2024-08-08', 'TRANSPORTATION', NOW(), NOW()),
('Restaurant Lunch', 24.60, '2024-08-11', 'FOOD', NOW(), NOW()),
('Electricity Bill', 138.75, '2024-08-14', 'UTILITIES', NOW(), NOW()),
('Gasoline', 61.20, '2024-08-17', 'TRANSPORTATION', NOW(), NOW()),
('Groceries', 87.40, '2024-08-22', 'FOOD', NOW(), NOW()),
('Phone Bill', 55.00, '2024-08-25', 'UTILITIES', NOW(), NOW()),

-- September 2024
('Monthly Rent', 400.00, '2024-09-01', 'HOUSING', NOW(), NOW()),
('Grocery Shopping', 189.70, '2024-09-04', 'FOOD', NOW(), NOW()),
('Water Bill', 39.30, '2024-09-06', 'UTILITIES', NOW(), NOW()),
('Bus Pass', 45.00, '2024-09-08', 'TRANSPORTATION', NOW(), NOW()),
('Coffee', 9.85, '2024-09-12', 'FOOD', NOW(), NOW()),
('Internet Bill', 65.00, '2024-09-15', 'UTILITIES', NOW(), NOW()),
('Gasoline', 56.70, '2024-09-18', 'TRANSPORTATION', NOW(), NOW()),
('Groceries', 94.25, '2024-09-23', 'FOOD', NOW(), NOW()),
('Gas Bill', 75.60, '2024-09-28', 'UTILITIES', NOW(), NOW()),

-- October 2024
('Monthly Rent', 400.00, '2024-10-01', 'HOUSING', NOW(), NOW()),
('Grocery Shopping', 207.15, '2024-10-03', 'FOOD', NOW(), NOW()),
('Electricity Bill', 122.40, '2024-10-05', 'UTILITIES', NOW(), NOW()),
('Uber Ride', 31.20, '2024-10-08', 'TRANSPORTATION', NOW(), NOW()),
('Pizza Delivery', 26.75, '2024-10-11', 'FOOD', NOW(), NOW()),
('Phone Bill', 55.00, '2024-10-15', 'UTILITIES', NOW(), NOW()),
('Gasoline', 63.80, '2024-10-18', 'TRANSPORTATION', NOW(), NOW()),
('Groceries', 81.90, '2024-10-23', 'FOOD', NOW(), NOW()),
('Water Bill', 43.50, '2024-10-28', 'UTILITIES', NOW(), NOW()),

-- November 2024
('Monthly Rent', 400.00, '2024-11-01', 'HOUSING', NOW(), NOW()),
('Grocery Shopping', 195.80, '2024-11-04', 'FOOD', NOW(), NOW()),
('Gas Bill', 88.20, '2024-11-06', 'UTILITIES', NOW(), NOW()),
('Bus Ticket', 15.75, '2024-11-08', 'TRANSPORTATION', NOW(), NOW()),
('Thanksgiving Dinner', 65.40, '2024-11-28', 'FOOD', NOW(), NOW()),
('Internet Bill', 65.00, '2024-11-15', 'UTILITIES', NOW(), NOW()),
('Gasoline', 58.90, '2024-11-18', 'TRANSPORTATION', NOW(), NOW()),
('Groceries', 88.70, '2024-11-23', 'FOOD', NOW(), NOW()),
('Electricity Bill', 135.60, '2024-11-25', 'UTILITIES', NOW(), NOW()),

-- December 2024
('Monthly Rent', 400.00, '2024-12-01', 'HOUSING', NOW(), NOW()),
('Grocery Shopping', 213.45, '2024-12-03', 'FOOD', NOW(), NOW()),
('Water Bill', 41.80, '2024-12-05', 'UTILITIES', NOW(), NOW()),
('Taxi Ride', 28.60, '2024-12-08', 'TRANSPORTATION', NOW(), NOW()),
('Holiday Dinner', 78.90, '2024-12-25', 'FOOD', NOW(), NOW()),
('Phone Bill', 55.00, '2024-12-15', 'UTILITIES', NOW(), NOW()),
('Gasoline', 65.30, '2024-12-18', 'TRANSPORTATION', NOW(), NOW()),
('Groceries', 92.15, '2024-12-22', 'FOOD', NOW(), NOW()),
('Gas Bill', 95.70, '2024-12-28', 'UTILITIES', NOW(), NOW()),

-- 2025 Data (January to August)
-- January 2025
('Monthly Rent', 400.00, '2025-01-01', 'HOUSING', NOW(), NOW()),
('Grocery Shopping', 198.25, '2025-01-03', 'FOOD', NOW(), NOW()),
('Electricity Bill', 142.80, '2025-01-05', 'UTILITIES', NOW(), NOW()),
('Bus Pass', 45.00, '2025-01-07', 'TRANSPORTATION', NOW(), NOW()),
('Restaurant', 34.50, '2025-01-12', 'FOOD', NOW(), NOW()),
('Internet Bill', 65.00, '2025-01-15', 'UTILITIES', NOW(), NOW()),
('Gasoline', 62.40, '2025-01-18', 'TRANSPORTATION', NOW(), NOW()),
('Groceries', 86.75, '2025-01-22', 'FOOD', NOW(), NOW()),
('Water Bill', 38.90, '2025-01-25', 'UTILITIES', NOW(), NOW()),

-- February 2025
('Monthly Rent', 400.00, '2025-02-01', 'HOUSING', NOW(), NOW()),
('Grocery Shopping', 203.60, '2025-02-04', 'FOOD', NOW(), NOW()),
('Gas Bill', 79.30, '2025-02-06', 'UTILITIES', NOW(), NOW()),
('Uber Ride', 21.80, '2025-02-08', 'TRANSPORTATION', NOW(), NOW()),
('Fast Food', 16.25, '2025-02-10', 'FOOD', NOW(), NOW()),
('Phone Bill', 55.00, '2025-02-15', 'UTILITIES', NOW(), NOW()),
('Gasoline', 59.70, '2025-02-18', 'TRANSPORTATION', NOW(), NOW()),
('Groceries', 91.40, '2025-02-22', 'FOOD', NOW(), NOW()),
('Electricity Bill', 128.50, '2025-02-25', 'UTILITIES', NOW(), NOW()),

-- March 2025
('Monthly Rent', 400.00, '2025-03-01', 'HOUSING', NOW(), NOW()),
('Grocery Shopping', 187.90, '2025-03-04', 'FOOD', NOW(), NOW()),
('Water Bill', 42.70, '2025-03-06', 'UTILITIES', NOW(), NOW()),
('Bus Ticket', 11.50, '2025-03-08', 'TRANSPORTATION', NOW(), NOW()),
('Lunch Out', 19.85, '2025-03-12', 'FOOD', NOW(), NOW()),
('Internet Bill', 65.00, '2025-03-15', 'UTILITIES', NOW(), NOW()),
('Gasoline', 64.80, '2025-03-18', 'TRANSPORTATION', NOW(), NOW()),
('Groceries', 89.60, '2025-03-22', 'FOOD', NOW(), NOW()),
('Gas Bill', 71.20, '2025-03-28', 'UTILITIES', NOW(), NOW()),

-- April 2025
('Monthly Rent', 400.00, '2025-04-01', 'HOUSING', NOW(), NOW()),
('Grocery Shopping', 209.75, '2025-04-03', 'FOOD', NOW(), NOW()),
('Electricity Bill', 118.90, '2025-04-05', 'UTILITIES', NOW(), NOW()),
('Taxi Ride', 26.40, '2025-04-08', 'TRANSPORTATION', NOW(), NOW()),
('Restaurant Dinner', 42.30, '2025-04-11', 'FOOD', NOW(), NOW()),
('Phone Bill', 55.00, '2025-04-15', 'UTILITIES', NOW(), NOW()),
('Gasoline', 57.60, '2025-04-18', 'TRANSPORTATION', NOW(), NOW()),
('Groceries', 84.20, '2025-04-22', 'FOOD', NOW(), NOW()),
('Water Bill', 40.15, '2025-04-25', 'UTILITIES', NOW(), NOW()),

-- May 2025
('Monthly Rent', 400.00, '2025-05-01', 'HOUSING', NOW(), NOW()),
('Grocery Shopping', 194.40, '2025-05-04', 'FOOD', NOW(), NOW()),
('Gas Bill', 66.80, '2025-05-06', 'UTILITIES', NOW(), NOW()),
('Bus Pass', 45.00, '2025-05-08', 'TRANSPORTATION', NOW(), NOW()),
('Coffee Shop', 13.75, '2025-05-12', 'FOOD', NOW(), NOW()),
('Internet Bill', 65.00, '2025-05-15', 'UTILITIES', NOW(), NOW()),
('Gasoline', 61.90, '2025-05-18', 'TRANSPORTATION', NOW(), NOW()),
('Groceries', 93.85, '2025-05-23', 'FOOD', NOW(), NOW()),
('Electricity Bill', 125.70, '2025-05-28', 'UTILITIES', NOW(), NOW()),

-- June 2025
('Monthly Rent', 400.00, '2025-06-01', 'HOUSING', NOW(), NOW()),
('Grocery Shopping', 201.20, '2025-06-03', 'FOOD', NOW(), NOW()),
('Water Bill', 44.60, '2025-06-05', 'UTILITIES', NOW(), NOW()),
('Uber Ride', 23.90, '2025-06-08', 'TRANSPORTATION', NOW(), NOW()),
('Takeout', 29.40, '2025-06-11', 'FOOD', NOW(), NOW()),
('Phone Bill', 55.00, '2025-06-15', 'UTILITIES', NOW(), NOW()),
('Gasoline', 58.30, '2025-06-18', 'TRANSPORTATION', NOW(), NOW()),
('Groceries', 87.95, '2025-06-22', 'FOOD', NOW(), NOW()),
('Gas Bill', 73.50, '2025-06-25', 'UTILITIES', NOW(), NOW()),

-- July 2025
('Monthly Rent', 400.00, '2025-07-01', 'HOUSING', NOW(), NOW()),
('Grocery Shopping', 216.80, '2025-07-04', 'FOOD', NOW(), NOW()),
('Electricity Bill', 148.30, '2025-07-07', 'UTILITIES', NOW(), NOW()),
('Bus Ticket', 14.25, '2025-07-09', 'TRANSPORTATION', NOW(), NOW()),
('Ice Cream', 10.50, '2025-07-12', 'FOOD', NOW(), NOW()),
('Internet Bill', 65.00, '2025-07-15', 'UTILITIES', NOW(), NOW()),
('Gasoline', 66.70, '2025-07-18', 'TRANSPORTATION', NOW(), NOW()),
('Groceries', 90.45, '2025-07-23', 'FOOD', NOW(), NOW()),
('Water Bill', 43.20, '2025-07-28', 'UTILITIES', NOW(), NOW()),

-- August 2025
('Monthly Rent', 400.00, '2025-08-01', 'HOUSING', NOW(), NOW()),
('Grocery Shopping', 205.15, '2025-08-03', 'FOOD', NOW(), NOW()),
('Gas Bill', 61.40, '2025-08-05', 'UTILITIES', NOW(), NOW()),
('Taxi Ride', 20.70, '2025-08-08', 'TRANSPORTATION', NOW(), NOW()),
('Restaurant', 36.80, '2025-08-11', 'FOOD', NOW(), NOW()),
('Phone Bill', 55.00, '2025-08-15', 'UTILITIES', NOW(), NOW()),
('Gasoline', 63.50, '2025-08-18', 'TRANSPORTATION', NOW(), NOW()),
('Groceries', 88.25, '2025-08-22', 'FOOD', NOW(), NOW()),
('Electricity Bill', 134.90, '2025-08-25', 'UTILITIES', NOW(), NOW());