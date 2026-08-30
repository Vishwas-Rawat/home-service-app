-- V6__add_address_id_to_bookings.sql
-- Add address_id foreign key column to booking.bookings table

Alter table booking.bookings
add column address_id Integer references customer.customer_addresses(id)