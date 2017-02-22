package cz.muni.fi.parser;

import com.sun.javafx.geom.Edge;
import cz.muni.fi.model.perday.RouteModel;
import cz.muni.fi.model.perperiod.TrafficModel;
import cz.muni.fi.model.structural.StructuralModel;
import cz.muni.fi.model.structural.dual.Arm;
import cz.muni.fi.model.structural.dual.DualGraph;
import cz.muni.fi.model.structural.dual.GeographicCoordinates;
import cz.muni.fi.model.structural.dual.Site;
import cz.muni.fi.model.structural.primal.Link;
import cz.muni.fi.model.structural.primal.Node;
import cz.muni.fi.model.structural.primal.PrimalGraph;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by vacullik on 22/02/2017.
 */
public class JsonParserImpl implements JsonParser {
    private JSONObject json;

    public JsonParserImpl(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new NullPointerException("Parameter inputStream is null");
        }
        this.json = getJsonObject(inputStream);
    }

    @Override
    public StructuralModel parseStructuralModel() {
        return StructuralModelParser.parseStructuralModel(json);
    }

    @Override
    public RouteModel parseRouteModel() {
        return null;
    }

    @Override
    public TrafficModel parseTrafficModel() {
        return null;
    }

    private JSONObject getJsonObject(InputStream is) throws IOException {
        return new JSONObject(getContentInString(is));
    }

    private String getContentInString(InputStream is) throws IOException {
        StringBuilder result = new StringBuilder();
        String line = "";
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    private static class StructuralModelParser {
        public static StructuralModel parseStructuralModel(JSONObject structuralJson) {
            //TODO do not pass structuralJson but parsed subjson
            PrimalGraph primalGraph = parsePrimalGraph(structuralJson);
            DualGraph dualGraph = parseDualGraph(structuralJson);
            return new StructuralModel(primalGraph, dualGraph);
        }

        private static PrimalGraph parsePrimalGraph(JSONObject primalGraphJson) {
            Map<Long, Node> nodes = parseNodesOfPrimalGraph(primalGraphJson);
            Set<Link> links = parseLinksOfPrimalGraph(primalGraphJson, nodes);
            return new PrimalGraph(nodes, links);
        }

        private static Map<Long, Node> parseNodesOfPrimalGraph(JSONObject primalGraphJson) {
            Map<Long, Node> resultNodes = new HashMap<>();
            JSONArray vertices = primalGraphJson.getJSONArray("V");
            for (int i = 0; i < vertices.length(); i++) {
                long key = vertices.getLong(i);
                Node node = new Node(key);
                resultNodes.put(key, node);
            }
            return resultNodes;
        }

        private static Set<Link> parseLinksOfPrimalGraph(JSONObject primalGraphJson, Map<Long, Node> nodes) {
            Set<Link> resultLinks = new HashSet<>();
            JSONArray edges = primalGraphJson.getJSONArray("E");
            for (int i = 0; i < edges.length(); i++) {
                JSONArray linkJson = edges.getJSONArray(i);
                Link link = parseLink(linkJson, nodes);
                resultLinks.add(link);
            }
            return resultLinks;
        }

        private static Link parseLink(JSONArray linkJson, Map<Long, Node> nodes) {
            long fromKey = linkJson.getLong(0);
            long toKey = linkJson.getLong(1);
            Node fromNode = nodes.get(fromKey);
            Node toNode = nodes.get(toKey);
            return new Link(fromNode, toNode);
        }

        private static DualGraph parseDualGraph(JSONObject dualGraphJson) {

            return null;
        }

        private static Map<Long, Site> parseSitesOfDualGraph(JSONObject dualGraphJson) {
            Map<Long, Site> resultSites = new HashMap<>();
            for (String key : dualGraphJson.keySet()) {
                JSONObject siteJson = dualGraphJson.getJSONObject(key);
                Site site = parseSiteWithoutArms(siteJson);
                resultSites.put(Long.parseLong(key), site);
            }
            for (Map.Entry<Long, Site> entry : resultSites.entrySet()) {
                String key = entry.getKey().toString();
                JSONArray siteArmsJson = dualGraphJson.getJSONObject(key).getJSONArray("arms");
                List<Arm> arms = parseSiteArms(siteArmsJson, resultSites);
                entry.getValue().setArms(arms);
            }
            return resultSites;
        }

        private static Site parseSiteWithoutArms(JSONObject siteJson) {
            double latitude = siteJson.getDouble("latitude");
            double longitude = siteJson.getDouble("longitude");
            GeographicCoordinates coordinates = new GeographicCoordinates(latitude, longitude);
            return new Site(coordinates);
        }

        private static List<Arm> parseSiteArms(JSONArray armsArrayJson, Map<Long, Site> sites) {
            List<Arm> resultArms = new ArrayList<>();
            for (int i = 0; i < armsArrayJson.length(); i++) {
                JSONObject armJson = armsArrayJson.getJSONObject(i);
                resultArms.add(parseArm(armJson, sites));
            }
            return resultArms;
        }

        private static Arm parseArm(JSONObject armJson, Map<Long, Site> sites) {
            Arm.Builder armBuilder = new Arm.Builder();
            int armAngle = armJson.getInt("arm_angle");
            armBuilder.armAngle(armAngle);
            int armNum = armJson.getInt("arm_num");
            armBuilder.armNum(armNum);
            int armOffset = armJson.getInt("arm_offset");
            armBuilder.armOffset(armOffset);
            int leftLanes = armJson.getInt("left_lanes");
            armBuilder.leftLanes(leftLanes);
            int rightLanes = armJson.getInt("right_lanes");
            armBuilder.rightLanes(rightLanes);
            long osmWayId = armJson.getLong("osm_way_id");
            armBuilder.osmWayId(osmWayId);
            String streetName = armJson.getString("street_name");
            armBuilder.streetName(streetName);
            long nextSiteId = armJson.getLong("next_site");
            armBuilder.nextSite(sites.get(nextSiteId));
            return armBuilder.build();
        }
    }
}
