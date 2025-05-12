package mg.nh.core.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import mg.nh.core.exception.CoreException;

@Service
public class IdGenerationService {

    private final Map<String, Integer> counters = new HashMap<>();

    public synchronized String generateId(String prefix) {
        if (prefix == null || prefix.length() != 3) {
            throw new CoreException("Invalid prefix: must be 3 letters");
        }
        int nextNumber = counters.getOrDefault(prefix, 0) + 1;
        counters.put(prefix, nextNumber);
        
        // format as PREFIX + 4-digit number (e.g., CLI0001)
        return String.format("%s%04d", prefix.toUpperCase(), nextNumber);
    }
}
