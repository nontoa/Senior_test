

--1. Calculate profits of the platform monthly in the 2020 year

SELECT to_char(t.transaction_date, 'YYYY-MM') AS date, SUM(m.value) AS profit
FROM movement m JOIN transaction t ON m.transaction_id = t.transaction_id
WHERE t.state = 'APPROVED' 
AND t.transaction_date BETWEEN '2020-01-01 00:00:00' AND '2020-12-31 23:59:59' 
AND m.movement_type = 'COMISSION'
GROUP BY date
ORDER BY date;

--2. Calculate total taxes for each client

SELECT t.client_id, c.name, SUM(m.value) total_taxes
FROM movement m 
JOIN transaction t ON m.transaction_id = t.transaction_id
JOIN client c ON t.client_id = c.client_id
WHERE t.state = 'APPROVED' AND m.movement_type = 'TAX'
group by t.client_id, c.name;

--3. Find for each client:
    --3.1 Total taxes
    --3.2 Commissions
    --3.3 Transaction values

SELECT c.client_id, c.name, m.movement_type, SUM(m.value) as total
FROM movement m 
JOIN transaction t ON m.transaction_id = t.transaction_id
JOIN client c ON t.client_id = c.client_id
WHERE t.state = 'APPROVED'
GROUP BY c.client_id, m.movement_type
ORDER BY c.client_id;

--4. Find approval rates by payment method

SELECT table1.payment_method, (table1.approved_txs * 100) / table2.total_txs AS approval_rate
FROM (SELECT payment_method, count(*) AS approved_txs
      FROM transaction 
      WHERE state = 'APPROVED' 
      GROUP BY payment_method) table1 JOIN
      (SELECT payment_method, count(*) AS total_txs
      FROM transaction 
      GROUP BY payment_method) table2
      ON table1.payment_method = table2.payment_method;

--5. Max transaction amount monthly for each client

SELECT to_char(transaction_date, 'YYYY-MM') AS date, c.name, MAX (value) AS Max_transaction_amount
FROM transaction t
JOIN client c on t.client_id = c.client_id
WHERE state = 'APPROVED'
GROUP BY c.name, date
ORDER BY date;