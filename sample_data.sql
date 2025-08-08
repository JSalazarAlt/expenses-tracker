-- Sample expense data for 2023 and 2024 (through November)
-- Consistent rent, similar utilities/groceries, variable entertainment

-- 2023 Data
INSERT INTO expenses (description, amount, date, category) VALUES
-- January 2023
('Monthly Rent', 1200.00, '2023-01-01', 'rent'),
('Electricity Bill', 85.50, '2023-01-05', 'utilities'),
('Grocery Shopping', 320.75, '2023-01-08', 'groceries'),
('Gas Station', 45.20, '2023-01-12', 'transportation'),
('Movie Night', 28.50, '2023-01-15', 'entertainment'),
('Grocery Shopping', 280.30, '2023-01-22', 'groceries'),
('Restaurant Dinner', 65.80, '2023-01-28', 'dining'),

-- February 2023
('Monthly Rent', 1200.00, '2023-02-01', 'rent'),
('Water Bill', 42.30, '2023-02-03', 'utilities'),
('Grocery Shopping', 295.60, '2023-02-07', 'groceries'),
('Concert Tickets', 120.00, '2023-02-14', 'entertainment'),
('Gas Station', 48.75, '2023-02-18', 'transportation'),
('Grocery Shopping', 310.45, '2023-02-25', 'groceries'),

-- March 2023
('Monthly Rent', 1200.00, '2023-03-01', 'rent'),
('Electricity Bill', 78.90, '2023-03-04', 'utilities'),
('Grocery Shopping', 335.20, '2023-03-09', 'groceries'),
('Gaming Purchase', 59.99, '2023-03-16', 'entertainment'),
('Gas Station', 52.40, '2023-03-20', 'transportation'),
('Grocery Shopping', 275.85, '2023-03-27', 'groceries'),

-- April 2023
('Monthly Rent', 1200.00, '2023-04-01', 'rent'),
('Water Bill', 38.75, '2023-04-05', 'utilities'),
('Grocery Shopping', 298.40, '2023-04-10', 'groceries'),
('Weekend Trip', 180.50, '2023-04-22', 'entertainment'),
('Gas Station', 46.30, '2023-04-25', 'transportation'),
('Grocery Shopping', 315.70, '2023-04-28', 'groceries'),

-- May 2023
('Monthly Rent', 1200.00, '2023-05-01', 'rent'),
('Electricity Bill', 92.15, '2023-05-06', 'utilities'),
('Grocery Shopping', 340.85, '2023-05-11', 'groceries'),
('Streaming Services', 35.97, '2023-05-15', 'subscriptions'),
('Gas Station', 55.80, '2023-05-19', 'transportation'),
('Grocery Shopping', 285.60, '2023-05-26', 'groceries'),

-- June 2023
('Monthly Rent', 1200.00, '2023-06-01', 'rent'),
('Water Bill', 45.20, '2023-06-04', 'utilities'),
('Grocery Shopping', 325.45, '2023-06-08', 'groceries'),
('Summer Festival', 95.00, '2023-06-17', 'entertainment'),
('Gas Station', 49.65, '2023-06-21', 'transportation'),
('Grocery Shopping', 305.30, '2023-06-29', 'groceries'),

-- July 2023
('Monthly Rent', 1200.00, '2023-07-01', 'rent'),
('Electricity Bill', 105.40, '2023-07-05', 'utilities'),
('Grocery Shopping', 315.75, '2023-07-09', 'groceries'),
('Beach Trip', 145.80, '2023-07-15', 'entertainment'),
('Gas Station', 58.90, '2023-07-18', 'transportation'),
('Grocery Shopping', 290.50, '2023-07-25', 'groceries'),

-- August 2023
('Monthly Rent', 1200.00, '2023-08-01', 'rent'),
('Water Bill', 41.85, '2023-08-03', 'utilities'),
('Grocery Shopping', 330.20, '2023-08-07', 'groceries'),
('Amusement Park', 75.50, '2023-08-12', 'entertainment'),
('Gas Station', 51.25, '2023-08-16', 'transportation'),
('Grocery Shopping', 295.80, '2023-08-23', 'groceries'),

-- September 2023
('Monthly Rent', 1200.00, '2023-09-01', 'rent'),
('Electricity Bill', 88.70, '2023-09-04', 'utilities'),
('Grocery Shopping', 310.65, '2023-09-08', 'groceries'),
('Sports Event', 110.00, '2023-09-16', 'entertainment'),
('Gas Station', 47.40, '2023-09-20', 'transportation'),
('Grocery Shopping', 285.95, '2023-09-27', 'groceries'),

-- October 2023
('Monthly Rent', 1200.00, '2023-10-01', 'rent'),
('Water Bill', 39.60, '2023-10-05', 'utilities'),
('Grocery Shopping', 345.30, '2023-10-09', 'groceries'),
('Halloween Party', 65.75, '2023-10-28', 'entertainment'),
('Gas Station', 53.80, '2023-10-15', 'transportation'),
('Grocery Shopping', 320.45, '2023-10-22', 'groceries'),

-- November 2023
('Monthly Rent', 1200.00, '2023-11-01', 'rent'),
('Electricity Bill', 95.25, '2023-11-06', 'utilities'),
('Grocery Shopping', 355.80, '2023-11-10', 'groceries'),
('Black Friday Shopping', 180.99, '2023-11-24', 'entertainment'),
('Gas Station', 49.90, '2023-11-18', 'transportation'),
('Grocery Shopping', 310.70, '2023-11-25', 'groceries'),

-- December 2023
('Monthly Rent', 1200.00, '2023-12-01', 'rent'),
('Water Bill', 44.15, '2023-12-04', 'utilities'),
('Grocery Shopping', 380.50, '2023-12-08', 'groceries'),
('Holiday Gifts', 250.00, '2023-12-20', 'gifts'),
('Gas Station', 56.30, '2023-12-15', 'transportation'),
('Grocery Shopping', 340.25, '2023-12-22', 'groceries'),

-- 2024 Data (January through November)
-- January 2024
('Monthly Rent', 1200.00, '2024-01-01', 'rent'),
('Electricity Bill', 89.75, '2024-01-05', 'utilities'),
('Grocery Shopping', 325.40, '2024-01-08', 'groceries'),
('Gas Station', 47.85, '2024-01-12', 'transportation'),
('Netflix Subscription', 15.49, '2024-01-15', 'subscriptions'),
('Grocery Shopping', 295.60, '2024-01-22', 'groceries'),
('New Year Celebration', 85.50, '2024-01-01', 'entertainment'),

-- February 2024
('Monthly Rent', 1200.00, '2024-02-01', 'rent'),
('Water Bill', 43.80, '2024-02-03', 'utilities'),
('Grocery Shopping', 310.25, '2024-02-07', 'groceries'),
('Valentine Dinner', 95.75, '2024-02-14', 'dining'),
('Gas Station', 52.40, '2024-02-18', 'transportation'),
('Grocery Shopping', 285.90, '2024-02-25', 'groceries'),

-- March 2024
('Monthly Rent', 1200.00, '2024-03-01', 'rent'),
('Electricity Bill', 82.60, '2024-03-04', 'utilities'),
('Grocery Shopping', 340.80, '2024-03-09', 'groceries'),
('Spring Break Trip', 220.00, '2024-03-16', 'entertainment'),
('Gas Station', 55.70, '2024-03-20', 'transportation'),
('Grocery Shopping', 305.45, '2024-03-27', 'groceries'),

-- April 2024
('Monthly Rent', 1200.00, '2024-04-01', 'rent'),
('Water Bill', 40.95, '2024-04-05', 'utilities'),
('Grocery Shopping', 315.70, '2024-04-10', 'groceries'),
('Easter Brunch', 68.50, '2024-04-01', 'dining'),
('Gas Station', 48.30, '2024-04-25', 'transportation'),
('Grocery Shopping', 290.85, '2024-04-28', 'groceries'),

-- May 2024
('Monthly Rent', 1200.00, '2024-05-01', 'rent'),
('Electricity Bill', 96.40, '2024-05-06', 'utilities'),
('Grocery Shopping', 335.60, '2024-05-11', 'groceries'),
('Mother\'s Day Gift', 75.00, '2024-05-12', 'gifts'),
('Gas Station', 59.20, '2024-05-19', 'transportation'),
('Grocery Shopping', 320.30, '2024-05-26', 'groceries'),

-- June 2024
('Monthly Rent', 1200.00, '2024-06-01', 'rent'),
('Water Bill', 46.75, '2024-06-04', 'utilities'),
('Grocery Shopping', 350.45, '2024-06-08', 'groceries'),
('Summer Concert', 125.00, '2024-06-15', 'entertainment'),
('Gas Station', 51.80, '2024-06-21', 'transportation'),
('Grocery Shopping', 315.90, '2024-06-29', 'groceries'),

-- July 2024
('Monthly Rent', 1200.00, '2024-07-01', 'rent'),
('Electricity Bill', 108.90, '2024-07-05', 'utilities'),
('Grocery Shopping', 330.25, '2024-07-09', 'groceries'),
('4th of July BBQ', 89.75, '2024-07-04', 'entertainment'),
('Gas Station', 62.40, '2024-07-18', 'transportation'),
('Grocery Shopping', 305.80, '2024-07-25', 'groceries'),

-- August 2024
('Monthly Rent', 1200.00, '2024-08-01', 'rent'),
('Water Bill', 44.20, '2024-08-03', 'utilities'),
('Grocery Shopping', 345.70, '2024-08-07', 'groceries'),
('State Fair', 95.50, '2024-08-17', 'entertainment'),
('Gas Station', 54.90, '2024-08-16', 'transportation'),
('Grocery Shopping', 320.45, '2024-08-23', 'groceries'),

-- September 2024
('Monthly Rent', 1200.00, '2024-09-01', 'rent'),
('Electricity Bill', 91.35, '2024-09-04', 'utilities'),
('Grocery Shopping', 325.90, '2024-09-08', 'groceries'),
('Football Game', 85.00, '2024-09-21', 'entertainment'),
('Gas Station', 49.75, '2024-09-20', 'transportation'),
('Grocery Shopping', 310.60, '2024-09-27', 'groceries'),

-- October 2024
('Monthly Rent', 1200.00, '2024-10-01', 'rent'),
('Water Bill', 41.85, '2024-10-05', 'utilities'),
('Grocery Shopping', 360.40, '2024-10-09', 'groceries'),
('Halloween Costume', 45.99, '2024-10-25', 'clothing'),
('Gas Station', 57.20, '2024-10-15', 'transportation'),
('Grocery Shopping', 335.75, '2024-10-22', 'groceries'),

-- November 2024
('Monthly Rent', 1200.00, '2024-11-01', 'rent'),
('Electricity Bill', 98.60, '2024-11-06', 'utilities'),
('Grocery Shopping', 370.85, '2024-11-10', 'groceries'),
('Thanksgiving Dinner', 125.50, '2024-11-28', 'dining'),
('Gas Station', 52.40, '2024-11-18', 'transportation'),
('Grocery Shopping', 345.30, '2024-11-25', 'groceries');