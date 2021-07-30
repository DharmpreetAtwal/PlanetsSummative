use planets;
create table planet_info (name varchar(50), 
						angle smallint,
                        population int, 
                        max_pop int, 
                        orbit_radius_AU float);
                        
insert into planet_info(name,  angle, population, max_pop, orbit_radius_AU)
values('EARTH', -144, 5000, 5000, 1),
('MARS', -64, 300, 1000, 1.5), 
('MERCURY', 133, 800, 1500, 0.49), 
('JUPITER', -95, 200, 1300, 2.9),
('NEPTUNE', -168, 200, 300, 2.5),
('SATURN', -34, 5000, 7700, 2.75), 
('URANUS', 29, 2800, 4900, 2), 
('VENUS', 128, 1400, 4000, 1.75);