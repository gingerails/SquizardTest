CREATE TABLE attachments (
    teacher_uuid        STRING PRIMARY KEY,
    text_attachments    BLOB,
    graphic_attachments BLOB,
    reference_sheets    BLOB,
    instruction_sheets  BLOB,
    question_used_on    STRING,
    test_used_on        STRING
);

CREATE TABLE course (
    courses_uuid STRING,
    course_num  STRING,
    sections    STRING,
    PRIMARY KEY (
        courses_uuid,
        course_num
    )
);

CREATE TABLE e_question (
    question_id          STRING PRIMARY KEY,
    question_content     STRING UNIQUE 
                                NOT NULL,
    question_graphic     BLOB,
    correct_answer       STRING NOT NULL,
    answer_graphic       BLOB,
    text_reference       STRING,
    reference_material   STRING,
    instructor_comments  STRING,
    grading_instructions STRING,
    class_average        DOUBLE
);

CREATE TABLE existing_tests (
    teacher_uuid  STRING,
    test_html     BLOB   NOT NULL,
    test_key_html BLOB   NOT NULL
);

CREATE TABLE feedback (
    test_uuid     STRING PRIMARY KEY,
    class_average DOUBLE,
    class         STRING,
    test_length   DOUBLE
);

CREATE TABLE fib_question (
    question_id         STRING PRIMARY KEY,
    question_content    STRING UNIQUE
                               NOT NULL,
    question_graphic    BLOB,
    correct_answer      STRING NOT NULL,
    answer_graphic      BLOB,
    text_ref_section    STRING,
    reference_material  STRING,
    instructor_comment  STRING,
    grading_instruction STRING,
    class_average       DOUBLE
);

CREATE TABLE mc_question (
    question_id         STRING PRIMARY KEY,
    question_content    STRING NOT NULL
                               UNIQUE,
    question_graphic    BLOB,
    correct_answer      STRING NOT NULL,
    false_answer        STRING NOT NULL,
    answer_graphic      BLOB,
    text_ref_section    STRING,
    reference_material  STRING,
    instructor_comment  STRING,
    grading_instruction STRING,
    class_average       STRING
);

CREATE TABLE m_question (
    question_id         STRING PRIMARY KEY,
    term                STRING NOT NULL,
    term_graphic        BLOB,
    answer              STRING NOT NULL,
    answer_graphic      BLOB,
    text_ref_section    STRING,
    reference_material  STRING,
    instructor_comment  STRING,
    grading_instruction STRING,
    class_average       DOUBLE
);

CREATE TABLE section (
    section_uuid STRING UNIQUE
                        NOT NULL,
    course_uuid  STRING NOT NULL,
    section_num  STRING NOT NULL,
    tests        STRING,
    PRIMARY KEY (
        section_uuid,
        section_num
    )
);

CREATE TABLE test (
    test_uuid           STRING PRIMARY KEY,
    section_uuid        STRING,
    date_used           STRING,
    true_false_q_ids    STRING,
    short_answer_q_ids  STRING,
    mult_choice_q_ids   STRING,
    fill_in_blank_q_ids STRING,
    matching_q_id       STRING,
    essay_q_ids         STRING,
    test_html           BLOB,
    test_key_html       BLOB
);

CREATE TABLE tf_question (
    question_id         STRING PRIMARY KEY,
    question_content    STRING UNIQUE
                               NOT NULL,
    question_graphic    BLOB,
    correct_answer      STRING NOT NULL,
    answer_graphic      BLOB,
    text_ref_section    STRING,
    reference_material  STRING,
    instructor_comment  STRING,
    grading_instruction STRING,
    class_average       DOUBLE
);

CREATE TABLE User (
    user_id  STRING PRIMARY KEY,
    username STRING NOT NULL
                    UNIQUE,
    password STRING NOT NULL
);