There is a providers table and a surveys table in a database.
 Providers can have many surveys and surveys belong to providers (via provider_id.)
 Surveys has a boolean column 'open'.
 Write sql to retrieve all providers that indicated they were open in a survey.

 --------------------------
 Providers                 |
 ============              |
 provider_id               |
                           |
 --------------------------

 --------------------------
 Surveys                    |
============                |
 survery_id                 |
 provider_id                |
 open                       |
                            |
-----------------------------


select * from providers prov,
join Surverys sur
on prov.provider_id= sur.provider_id
where sur.open is true;