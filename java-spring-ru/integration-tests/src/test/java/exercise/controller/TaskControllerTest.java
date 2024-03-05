package exercise.controller;

import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

import org.instancio.Instancio;
import org.instancio.Select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import exercise.repository.TaskRepository;
import exercise.model.Task;

// BEGIN
@SpringBootTest
@AutoConfigureMockMvc
// END
class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;


    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Spring!");
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }


    private Task generateTask() {
        return Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.lorem().word())
                .supply(Select.field(Task::getDescription), () -> faker.lorem().paragraph())
                .create();
    }

    // BEGIN
    @Test
    public void testCertainTask() throws Exception {
        var task = generateTask();
        task = taskRepository.save(task);
        var result = mockMvc.perform(get("/tasks/" + task.getId()))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        var res = om.readValue(body, Task.class);
        assertThat(res.getTitle()).isEqualTo(task.getTitle());
        assertThat(res.getId()).isEqualTo(task.getId());
    }

    @Test
    public void testCreateTask() throws Exception {
        var newtask = new Task();
        newtask.setTitle("new task");
        var result = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(newtask)))
                .andExpect(status().isCreated())
                .andReturn();
        var body = result.getResponse().getContentAsString();
        var res = om.readValue(body, Task.class);
        var resTask = taskRepository.findById(res.getId());

        assertThat(resTask).isPresent();
        String resTaskTitle = resTask.get().getTitle();
        assertThat(resTaskTitle).isEqualTo(newtask.getTitle());
    }

    @Test
    public void testEditTask() throws Exception {
        var task = generateTask();
        taskRepository.save(task);
        task.setTitle("new new title");
        var result = mockMvc.perform(put("/tasks/" + task.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andReturn();
        var body = result.getResponse().getContentAsString();
        var res = om.readValue(body, Task.class);
        assertThat(res.getTitle()).isEqualTo(task.getTitle());
        var resTask = taskRepository.findById(res.getId());

        assertThat(resTask).isPresent();
        String resTaskTitle = resTask.get().getTitle();
        assertThat(resTaskTitle).isEqualTo(task.getTitle());
    }
    @Test
    public void testDeleteTask() throws Exception {
        var task = generateTask();
        taskRepository.save(task);
        var result = mockMvc.perform(delete("/tasks/" + task.getId()))
                .andExpect(status().isOk());
        var resTask = taskRepository.findById(task.getId());

        assertThat(resTask).isNotPresent();

    }
    // END
}
