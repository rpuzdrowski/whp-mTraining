package org.motechproject.whp.mtraining.domain;

import org.apache.commons.lang.StringUtils;
import org.motechproject.mtraining.dto.AnswerDto;
import org.motechproject.mtraining.dto.ChapterDto;
import org.motechproject.mtraining.dto.CourseDto;
import org.motechproject.mtraining.dto.MessageDto;
import org.motechproject.mtraining.dto.ModuleDto;
import org.motechproject.mtraining.dto.QuestionDto;
import org.motechproject.mtraining.dto.QuizDto;

import java.util.ArrayList;
import java.util.List;

public enum ContentType {
    COURSE {
        @Override
        public CourseDto toDto(String nodeName, String description, String fileName, boolean isActive, Integer numberOfQuizQuestions,
                               List<String> options, String correctAnswer, String correctAnswerFileName, Long passPercentage, List<Object> childDtos) {
            return new CourseDto(isActive, nodeName, description, (List<ModuleDto>) (Object) childDtos);
        }
    },
    MODULE {
        @Override
        public ModuleDto toDto(String nodeName, String description, String fileName, boolean isActive, Integer numberOfQuizQuestions,
                               List<String> options, String correctAnswer, String correctAnswerFileName, Long passPercentage, List<Object> childDtos) {
            return new ModuleDto(isActive, nodeName, description, (List<ChapterDto>) (Object) childDtos);
        }
    },
    CHAPTER {
        @Override
        public ChapterDto toDto(String nodeName, String description, String fileName, boolean isActive, Integer numberOfQuizQuestions,
                                List<String> options, String correctAnswer, String correctAnswerFileName, Long passPercentage, List<Object> childDtos) {
            List<QuestionDto> questions = filterChildNodesOfType(childDtos, QuestionDto.class);
            List<MessageDto> messages = filterChildNodesOfType(childDtos, MessageDto.class);
            return numberOfQuizQuestions > 0 ? new ChapterDto(isActive, nodeName, description, messages, new QuizDto(true, questions, numberOfQuizQuestions, passPercentage)) :
                    new ChapterDto(isActive, nodeName, description, messages, null);
        }

        private <T> List<T> filterChildNodesOfType(List<Object> childDtos, Class<T> classType) {
            List<T> childrenOfGivenType = new ArrayList<>();
            for (Object childDto : childDtos) {
                if (childDto.getClass().equals(classType)) {
                    childrenOfGivenType.add((T) childDto);
                }
            }
            return childrenOfGivenType;
        }
    },
    MESSAGE {
        @Override
        public MessageDto toDto(String nodeName, String description, String fileName, boolean isActive, Integer numberOfQuizQuestions,
                                List<String> options, String correctAnswer, String correctAnswerFileName, Long passPercentage, List<Object> childDtos) {
            return new MessageDto(isActive, nodeName, fileName, description);
        }
    },
    QUESTION {
        @Override
        public QuestionDto toDto(String nodeName, String description, String fileName, boolean isActive, Integer numberOfQuizQuestions,
                                 List<String> options, String correctAnswer, String correctAnswerFileName, Long passPercentage, List<Object> childDtos) {
            return new QuestionDto(isActive, nodeName, description, fileName, new AnswerDto(correctAnswer, correctAnswerFileName), options);
        }
    };

    public static ContentType from(String nodeType) {
        return ContentType.valueOf(StringUtils.trimToEmpty(nodeType).toUpperCase());
    }

    public abstract Object toDto(String nodeName, String description, String fileName, boolean isActive, Integer numberOfQuizQuestions,
                                 List<String> options, String correctAnswer, String correctAnswerFileName, Long passPercentage, List<Object> childDtos);
}