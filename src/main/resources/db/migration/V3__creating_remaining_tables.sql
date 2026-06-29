-- V3__create_remaining_tables.sql

-- 1. CATALOG SCHEMA: Service categories
CREATE TABLE IF NOT EXISTS catalog.categories (
    category_id SERIAL PRIMARY KEY,
    name VARCHAR(200) UNIQUE NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- 2. CUSTOMER SCHEMA: Profiles and Addresses
CREATE TABLE IF NOT EXISTS customer.customer_profiles (
    customer_id SERIAL PRIMARY KEY,
    user_id INTEGER UNIQUE NOT NULL REFERENCES auth.users(user_id) ON DELETE CASCADE, -- Matches auth.users(user_id)
    first_name VARCHAR(200) NOT NULL,
    last_name VARCHAR(200) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS customer.customer_addresses (
    id SERIAL PRIMARY KEY,
    customer_id INTEGER NOT NULL REFERENCES customer.customer_profiles(customer_id) ON DELETE CASCADE,
    address_line1 VARCHAR(255) NOT NULL,
    address_line2 VARCHAR(255),
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    is_default BOOLEAN DEFAULT false NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- 3. PROVIDER SCHEMA: Profiles and Skills
CREATE TABLE IF NOT EXISTS provider.provider_profiles (
    provider_id SERIAL PRIMARY KEY,
    user_id INTEGER UNIQUE NOT NULL REFERENCES auth.users(user_id) ON DELETE CASCADE, -- Matches auth.users(user_id)
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    shop_name VARCHAR(150),
    shop_details TEXT,
    is_available BOOLEAN DEFAULT true NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS provider.provider_skills (
    id SERIAL PRIMARY KEY,
    provider_id INTEGER NOT NULL REFERENCES provider.provider_profiles(provider_id) ON DELETE CASCADE,
    category_id INTEGER NOT NULL REFERENCES catalog.categories(category_id) ON DELETE CASCADE,
    UNIQUE (provider_id, category_id) -- Prevents duplicate skills
);

-- 4. BOOKING SCHEMA: Bookings and status changes
CREATE TABLE IF NOT EXISTS booking.bookings (
    booking_id SERIAL PRIMARY KEY,
    customer_id INTEGER NOT NULL REFERENCES customer.customer_profiles(customer_id),
    provider_id INTEGER REFERENCES provider.provider_profiles(provider_id), -- Nullable until accepted
    category_id INTEGER NOT NULL REFERENCES catalog.categories(category_id),
    original_description TEXT NOT NULL,
    improved_description TEXT,
    status VARCHAR(50) DEFAULT 'REQUESTED' NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS booking.booking_status_history (
    id SERIAL PRIMARY KEY,
    booking_id INTEGER NOT NULL REFERENCES booking.bookings(booking_id) ON DELETE CASCADE,
    status VARCHAR(50) NOT NULL,
    notes VARCHAR(255),
    changed_by INTEGER REFERENCES auth.users(user_id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- 5. ADMIN SCHEMA: Audit logs
CREATE TABLE IF NOT EXISTS admin.admin_audit_logs (
    admin_audit_id SERIAL PRIMARY KEY,
    admin_id INTEGER NOT NULL REFERENCES auth.users(user_id),
    action VARCHAR(255) NOT NULL,
    target VARCHAR(255) NOT NULL,
    details TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- 6. AI SCHEMA: History of prompts and completions
CREATE TABLE IF NOT EXISTS ai.ai_logs (
    ai_logs_id SERIAL PRIMARY KEY,
    booking_id INTEGER REFERENCES booking.bookings(booking_id) ON DELETE SET NULL,
    prompt_type VARCHAR(100) NOT NULL,
    input_text TEXT NOT NULL,
    output_text TEXT NOT NULL,
    model_name VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
