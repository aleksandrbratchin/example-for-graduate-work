package ru.skypro.homework.mapper;


class CommentMapperTest {
//
//    private User userTest;
//    private Comment commentTest;
//
//    private LocalDateTime dateTest;
//
//    @BeforeEach
//    public void setUp() {
//        userTest = new User();
//        userTest.setId(1L);
//        userTest.setUsername("test");
//
//
//
//        commentTest = Comment.builder()
//                .id(1L)
//                .text("testtesttest")
//                .user(userTest)
//                .createdAt(dateTest)
//                .build();
//    }
//
//    @Test
//    void equalityCommentAndCommentResponse() {
//
//
//        CommentResponse commentResponseTest = new CommentResponse();
//        commentResponseTest.setPk(1);
//        commentResponseTest.setText("testtesttest");
//        commentResponseTest.setAuthor(userTest.getId().intValue());
//        commentResponseTest.setCreatedAt((int) dateTest.getTime());
//
//        assertThat(commentTest.getId()).isEqualTo(commentResponseTest.getPk().longValue());
//        assertThat(commentTest.getText()).isEqualTo(commentResponseTest.getText());
//        assertThat(commentTest.getUser().getId().intValue()).isEqualTo(commentResponseTest.getAuthor());
//        assertThat(commentTest.getCreatedAt().getTime()).isEqualTo(commentResponseTest.getCreatedAt().longValue());
//
//    }
//
//    @Test
//    void equalityCommentAndCreateOrUpdateComment() {
//
//        CreateOrUpdateComment createOrUpdateCommentTest = new CreateOrUpdateComment("testtesttest");
//
//        assertThat(commentTest.getText()).isEqualTo(createOrUpdateCommentTest.getText());
//    }
//
//    @Test
//    void toListWithDto() {
//
//        List <Comment> listBasiq = List.of(commentTest);
//
//        CommentResponse commentResponseTest = new CommentResponse();
//        commentResponseTest.setPk(1);
//        commentResponseTest.setText("testtesttest");
//        commentResponseTest.setAuthor(userTest.getId().intValue());
//        commentResponseTest.setCreatedAt(12);
//
//        List <CommentResponse> listDTO = List.of(commentResponseTest);
//
//        assertThat(listBasiq.size()).isEqualTo(listDTO.size());
//
//    }
//
//    @Test
//    void equalityOfCommentsResponse() {
//        CommentResponse commentResponseTest = new CommentResponse();
//        commentResponseTest.setPk(1);
//        commentResponseTest.setText("testtesttest");
//        commentResponseTest.setAuthor(userTest.getId().intValue());
//        commentResponseTest.setCreatedAt(12);
//
//        int countExp = 1;
//        List <CommentResponse> resultExp = List.of(commentResponseTest);
//        List <CommentResponse> aq = List.of(commentResponseTest);
//
//        assertThat(resultExp.size()).isEqualTo(countExp);
//        assertThat(resultExp).isEqualTo(aq);
//    }
}