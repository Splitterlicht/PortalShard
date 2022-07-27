package grafnus.portalshard.gui;

import grafnus.portalshard.database.data.PortalData;
import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.Item;
import me.lucko.helper.menu.paginated.PaginatedGui;
import me.lucko.helper.menu.paginated.PaginatedGuiBuilder;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Function;

public class PlayerSelectorMenu extends PaginatedGui {

    private PortalData portal;

    public PlayerSelectorMenu(Function<PaginatedGui, List<Item>> content, Player player, PaginatedGuiBuilder model, PortalData data) {
        super(content, player, model);
        this.portal = data;
    }

    public PortalData getPortal() {
        return this.portal;
    }
}
