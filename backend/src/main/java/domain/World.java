package domain;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.JSONable;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Oliver Lea
 */
public class World implements JSONable {


    private List<Agent> agents;

    public World(List<Agent> agents) {
        this.agents = agents;
    }

    public void tick() {
        agents.forEach(a -> a.tick(this));
        agents = agents.stream().filter(Agent::isAlive).collect(toList());
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("agents", new JSONArray(
                this.agents.stream().map(Agent::toJSON).collect(toList())
        ));
        return obj;
    }

    public List<Agent> getAgents() {
        return agents;
    }
}
