SET FOREIGN_KEY_CHECKS = 0;
truncate table blog_post;
truncate table author;
truncate table comment;
truncate table author_posts;

INSERT INTO blog_post(id, title, content, date_created)
VALUES (41, 'Title Post 1', 'Post content 1', '2021-06-03T11:33:56'),
        (42, 'Title Post 2', 'Post content 2', '2021-06-03T11:34:56'),
        (43, 'Title Post 3', 'Post content 3', '2021-06-03T11:35:56'),
        (44, 'Title Post 4', 'Post content 4', '2021-06-03T11:36:56'),
        (45, 'Title Post 5', 'Post content 5', '2021-06-03T11:37:56');

SET FOREIGN_KEY_CHECKS = 1;