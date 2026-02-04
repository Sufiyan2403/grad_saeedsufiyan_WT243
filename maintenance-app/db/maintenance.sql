-- Layout Maintenance Application

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'OWNER')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE owners (
    owner_id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(user_id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(15),
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE sites (
    site_id SERIAL PRIMARY KEY,
    site_number INTEGER UNIQUE NOT NULL,
    site_type VARCHAR(50) NOT NULL CHECK (site_type IN ('Villa', 'Apartment', 'Independent House', 'Open site')),
    dimensions VARCHAR(20) NOT NULL, 
    area_sqft INTEGER NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'Open' CHECK (status IN ('Open', 'Under Construction', 'Built')),
    owner_id INTEGER REFERENCES owners(owner_id) ON DELETE SET NULL,
    maintenance_rate DECIMAL(10,2) NOT NULL, 
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE maintenance_payments (
    payment_id SERIAL PRIMARY KEY,
    site_id INTEGER REFERENCES sites(site_id) ON DELETE CASCADE,
    payment_date DATE NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_month VARCHAR(7) NOT NULL,
    collected_by INTEGER REFERENCES users(user_id),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE approval_requests (
    request_id SERIAL PRIMARY KEY,
    site_id INTEGER REFERENCES sites(site_id) ON DELETE CASCADE,
    requested_by INTEGER REFERENCES users(user_id),
    request_type VARCHAR(50) NOT NULL, 
    old_value TEXT,
    new_value TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED')),
    admin_notes TEXT,
    approved_by INTEGER REFERENCES users(user_id),
    request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    response_date TIMESTAMP
);


INSERT INTO users (username, password, role) VALUES ('admin', 'admin123', 'ADMIN');


-- First 10 sites: 40x60 (2400 sqft)
INSERT INTO sites (site_number, site_type, dimensions, area_sqft, status, maintenance_rate) VALUES
(1, 'Open site', '40x60', 2400, 'Open', 6.00),
(2, 'Open site', '40x60', 2400, 'Open', 6.00),
(3, 'Open site', '40x60', 2400, 'Open', 6.00),
(4, 'Open site', '40x60', 2400, 'Open', 6.00),
(5, 'Open site', '40x60', 2400, 'Open', 6.00),
(6, 'Open site', '40x60', 2400, 'Open', 6.00),
(7, 'Open site', '40x60', 2400, 'Open', 6.00),
(8, 'Open site', '40x60', 2400, 'Open', 6.00),
(9, 'Open site', '40x60', 2400, 'Open', 6.00),
(10, 'Open site', '40x60', 2400, 'Open', 6.00);

-- Next 10 sites: 30x50 (1500 sqft)
INSERT INTO sites (site_number, site_type, dimensions, area_sqft, status, maintenance_rate) VALUES
(11, 'Open site', '30x50', 1500, 'Open', 6.00),
(12, 'Open site', '30x50', 1500, 'Open', 6.00),
(13, 'Open site', '30x50', 1500, 'Open', 6.00),
(14, 'Open site', '30x50', 1500, 'Open', 6.00),
(15, 'Open site', '30x50', 1500, 'Open', 6.00),
(16, 'Open site', '30x50', 1500, 'Open', 6.00),
(17, 'Open site', '30x50', 1500, 'Open', 6.00),
(18, 'Open site', '30x50', 1500, 'Open', 6.00),
(19, 'Open site', '30x50', 1500, 'Open', 6.00),
(20, 'Open site', '30x50', 1500, 'Open', 6.00);

-- Last 15 sites: 30x40 (1200 sqft)
INSERT INTO sites (site_number, site_type, dimensions, area_sqft, status, maintenance_rate) VALUES
(21, 'Open site', '30x40', 1200, 'Open', 6.00),
(22, 'Open site', '30x40', 1200, 'Open', 6.00),
(23, 'Open site', '30x40', 1200, 'Open', 6.00),
(24, 'Open site', '30x40', 1200, 'Open', 6.00),
(25, 'Open site', '30x40', 1200, 'Open', 6.00),
(26, 'Open site', '30x40', 1200, 'Open', 6.00),
(27, 'Open site', '30x40', 1200, 'Open', 6.00),
(28, 'Open site', '30x40', 1200, 'Open', 6.00),
(29, 'Open site', '30x40', 1200, 'Open', 6.00),
(30, 'Open site', '30x40', 1200, 'Open', 6.00),
(31, 'Open site', '30x40', 1200, 'Open', 6.00),
(32, 'Open site', '30x40', 1200, 'Open', 6.00),
(33, 'Open site', '30x40', 1200, 'Open', 6.00),
(34, 'Open site', '30x40', 1200, 'Open', 6.00),
(35, 'Open site', '30x40', 1200, 'Open', 6.00);


select * from sites
select * from owners
select * from maintenance_payments
