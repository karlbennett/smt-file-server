package shiver.me.timbers.file.io;

import org.apache.commons.io.IOUtils;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static shiver.me.timbers.file.io.FileConstants.FILE_FOUR;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE;
import static shiver.me.timbers.file.io.FileConstants.FILE_THREE;
import static shiver.me.timbers.file.io.FileConstants.FILE_TWO;

public class StreamFileSteps {

    public static void The_file_should_produce_an_input_stream(StreamFileCreator creator) {

        The_file_should_produce_an_input_stream(creator.create(FILE_ONE.getAbsolutePath()));
        The_file_should_produce_an_input_stream(creator.create(FILE_TWO.getAbsolutePath()));
        The_file_should_produce_an_input_stream(creator.create(FILE_THREE.getAbsolutePath()));
        The_file_should_produce_an_input_stream(creator.create(FILE_FOUR.getAbsolutePath()));
    }

    private static void The_file_should_produce_an_input_stream(StreamFile file) {

        assertNotNull("the file should produce an input stream.", file.getInputStream());
    }

    public static void The_files_input_stream_should_contain(StreamFileCreator creator) {

        The_files_input_stream_should_contain(new DefaultFileContentGetter<String>(), creator);
    }

    public static void The_files_input_stream_should_contain(
            DefaultFileContentGetter<String> getter, StreamFileCreator creator) {

        The_files_input_stream_should_read_to(getter.get(FILE_ONE), creator.create(FILE_ONE.getAbsolutePath()));
        The_files_input_stream_should_read_to(getter.get(FILE_TWO), creator.create(FILE_TWO.getAbsolutePath()));
        The_files_input_stream_should_read_to(getter.get(FILE_THREE), creator.create(FILE_THREE.getAbsolutePath()));
        The_files_input_stream_should_read_to(getter.get(FILE_FOUR), creator.create(FILE_FOUR.getAbsolutePath()));
    }

    private static void The_files_input_stream_should_read_to(String text, StreamFile file) {

        try {

            assertEquals("the files text should be correct.", text, IOUtils.toString(file.getInputStream()));

        } catch (IOException e) {

            throw new AssertionError(e);
        }
    }
}
