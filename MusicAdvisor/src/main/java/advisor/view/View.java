package advisor.view;

import advisor.Main;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class View {
    public static View getInstance() {
        if (instance == null) {
            instance = new View();
        }
        return instance;
    }

    private List<String> normalView;
    private List<String> pagedView;
    private boolean paged;
    private int pageNumber;
    private int pagesCount;

    public List<String> getView() {
        if (paged) {
            return getCurrentPageView();
        }
        return normalView;
    }

    private List<String> getCurrentPageView() {
        if (!paged || pageNumber > pagesCount - 1 || pageNumber < 0) {
            return List.of(noMorePagesMsg);
        }

        int firstEntryIndex = Main.entriesPerPage * pageNumber;
        int lastEntryIndex = Math.min(firstEntryIndex + Main.entriesPerPage, pagedView.size());
        List<String> currentPageStrings = new LinkedList<>();
        for (int i = firstEntryIndex; i < lastEntryIndex; i++) {
            currentPageStrings.add(pagedView.get(i));
        }
        currentPageStrings.add(getCurrentPageInfo());
        return currentPageStrings;
    }

    public void setNextPage() {
        // Range: [1, pagesCount]
        pageNumber = Math.max(1, Math.min(pagesCount, pageNumber + 1));
    }

    public void setPrevPage() {
        // Range: [-1, pagesCount - 2]
        pageNumber = Math.max(-1, Math.min(pagesCount - 2, pageNumber - 1));
    }

    public String getViewString() {
        return getAsString(getView());
    }

    public void setNormalView(String viewString) {
        setNormalView(getAsList(viewString));
    }

    public void setNormalView(List<String> stringList) {
        normalView = (stringList != null) ? stringList : Collections.emptyList();
        paged = false;
    }

    public void setPagedView(List<String> stringList) {
        pagedView = new LinkedList<>(stringList);
        paged = true;
        pageNumber = 0;
        pagesCount = stringList.size() / Main.entriesPerPage;
    }

    String getAsString(List<String> stringList) {
        return String.join("\n", stringList);
    }

    List<String> getAsList(String s) {
        if (s == null || s.isEmpty()) {
            return Collections.emptyList();
        }
        return List.of(s);
    }

    String getCurrentPageInfo() {
        return String.format("---PAGE %d OF %d---", pageNumber + 1, pagesCount);
    }

    private static final String noMorePagesMsg = "No more pages.";

    private static View instance;

    private View() {
        pagedView = Collections.emptyList();
        normalView = Collections.emptyList();
        paged = false;
    }
}
