drop table if exists dynamic_lifecycle_plugin;

/*==============================================================*/
/* Table: dynamic_lifecycle_plugin                                                        */
/*==============================================================*/
create table dynamic_lifecycle_plugin
(
   id                   bigint(20) not null comment 'The primary key',
   class_name            varchar(128) comment 'The plugin class full name',
   plugin_code                 varchar(64) comment 'The plugin code',
   plugin_name          varchar(64) comment 'The plugin name',
   plugin_version       varchar(32) comment 'The plugin version',
   jar_path             varchar(128) comment 'The plugin jar path',
   active_state         tinyint(4) comment 'The active state, 0:Unactivated(default) 1:activated',
   active_time           datetime comment 'The active time',
   gmt_create           datetime comment 'The install time',
   gmt_modified         datetime comment 'The modified time',
   primary key (id)
);
-- alter table dynamic_lifecycle_plugin comment 'The dynamic-lifecycle plugin';
